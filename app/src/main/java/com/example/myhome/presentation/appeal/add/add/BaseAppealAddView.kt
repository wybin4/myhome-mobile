package com.example.myhome.presentation.appeal.add.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myhome.common.models.ApartmentGetModel
import com.example.myhome.common.models.TypeOfServiceGetModel
import com.example.myhome.databinding.AppealAddViewItemsBinding
import com.example.myhome.databinding.DatePickersViewBinding
import com.example.myhome.utils.pickers.ImagePicker
import com.example.myhome.utils.InputValidator
import com.example.myhome.utils.managers.DatePickersManager
import com.example.myhome.utils.managers.SelectorManager
import com.example.myhome.utils.models.Resource

abstract class BaseAppealAddView : Fragment() {
    protected lateinit var layoutItemsBinding: AppealAddViewItemsBinding
    private lateinit var datePickersBinding: DatePickersViewBinding

    val viewModel by viewModels<BaseAppealAddViewModel>()

    private lateinit var apartmentManager: SelectorManager<ApartmentGetModel>
    private lateinit var typeOfServiceManager: SelectorManager<TypeOfServiceGetModel>

    private lateinit var datePickersManager: DatePickersManager
    private lateinit var factoryNumberValidator: InputValidator

    protected lateinit var imagePicker: ImagePicker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutItemsBinding.viewModel = viewModel

        datePickersBinding = layoutItemsBinding.datePickersLayout
        datePickersManager = DatePickersManager(datePickersBinding, requireActivity(), viewModel)

        setupValidator()
        setupSelectors()

        setupImagePicker()

        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apartmentList.observe(viewLifecycleOwner) { apartments ->
            apartmentManager.update(apartments)
        }
        viewModel.typeOfServiceList.observe(viewLifecycleOwner) { typesOfService ->
            typeOfServiceManager.update(typesOfService)
        }
        observeResourceState()
    }

    private fun observeResourceState() {
        viewModel.appealState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> showLoadingState()
                is Resource.Success -> showSuccessState()
                is Resource.Error -> showErrorState()
                else -> showErrorState()
            }
        }
    }

    private fun showLoadingState() {
//        TODO("Доделать")
    }

    protected open fun showSuccessState() {}

    private fun showErrorState() {
        TODO("Доделать")
    }

    private fun setupSelectors() {
        apartmentManager = SelectorManager(
            requireActivity(),
            layoutItemsBinding.apartmentList, layoutItemsBinding.apartmentSelector, "квартиру",
            viewModel::selectedApartmentId
        )

        typeOfServiceManager = SelectorManager(
            requireActivity(),
            layoutItemsBinding.typeOfServiceList, layoutItemsBinding.typeOfServiceSelector, "тип услуги",
            viewModel::selectedTypeOfServiceId
        )
    }

    private fun setupImagePicker() {
        imagePicker = ImagePicker(this) {
            viewModel.selectAttachment = it
            viewModel.addMeter()
        }
    }

    private fun setupValidator() {
        factoryNumberValidator = InputValidator(
            layoutItemsBinding.factoryNumberInput,
            { text: String? -> text?.length!! > 0 },
            "Введите заводской номер",
            {
                layoutItemsBinding.factoryNumberInput.editText?.doOnTextChanged { _, _, _, _ ->
                    factoryNumberValidator.validate(viewModel.selectedFactoryNumber)
                }
            }
        )
    }

    fun validate(): Boolean {
        val isFactoryNumberValid = factoryNumberValidator.validate(viewModel.selectedFactoryNumber)
        val isApartmentValid = apartmentManager.validate()
        val isTypeOfServiceValid = typeOfServiceManager.validate()
        val isDatePickersValid = datePickersManager.isDatePickersValid()
        return isFactoryNumberValid && isApartmentValid && isTypeOfServiceValid && isDatePickersValid
    }

}
