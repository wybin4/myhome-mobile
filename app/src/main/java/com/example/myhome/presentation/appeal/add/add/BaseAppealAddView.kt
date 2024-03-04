package com.example.myhome.presentation.appeal.add.add

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.annotation.VisibleForTesting
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myhome.common.models.ApartmentGetModel
import com.example.myhome.common.models.TypeOfServiceGetModel
import com.example.myhome.databinding.AppealAddViewItemsBinding
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.databinding.DatePickersViewBinding
import com.example.myhome.utils.pickers.ImagePicker
import com.example.myhome.utils.InputValidator
import com.example.myhome.utils.managers.DatePickersManager
import com.example.myhome.utils.managers.SelectorManager
import com.example.myhome.utils.managers.state.data.DataStateManager
import com.example.myhome.utils.models.Resource

abstract class BaseAppealAddView : Fragment() {
    protected lateinit var layoutItemsBinding: AppealAddViewItemsBinding
    private lateinit var datePickersBinding: DatePickersViewBinding
    protected lateinit var dataStateBinding: DataStateBinding

    val viewModel by viewModels<BaseAppealAddViewModel>()

    lateinit var apartmentManager: SelectorManager<ApartmentGetModel>
    lateinit var typeOfServiceManager: SelectorManager<TypeOfServiceGetModel>

    lateinit var datePickersManager: DatePickersManager
    private lateinit var factoryNumberValidator: InputValidator

    lateinit var imagePicker: ImagePicker

    protected lateinit var dataStateManager: DataStateManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        layoutItemsBinding.viewModel = viewModel

        datePickersBinding = layoutItemsBinding.datePickersLayout
        datePickersManager = DatePickersManager(
            datePickersBinding, requireActivity(),
            viewModel::selectVerifiedAt, viewModel::selectIssuedAt
        )

        setupValidator()
        setupSelectors()
        setupDateManager(inflater, container)

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
        viewModel.dataState.observe(viewLifecycleOwner) { resource ->
            dataStateManager.observeGetState(resource)
        }
        viewModel.dataAddState.observe(viewLifecycleOwner) { resource ->
            dataStateManager.observeAddState(resource)
        }
    }

    protected open fun setupDateManager(inflater: LayoutInflater, container: ViewGroup?) {
        dataStateBinding = DataStateBinding.inflate(inflater, container, false)
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
