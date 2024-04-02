package com.example.myhome.presentation.features.appeal.add.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myhome.databinding.AppealAddViewItemsBinding
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.databinding.DatePickersViewBinding
import com.example.myhome.presentation.features.common.models.ApartmentExtendedUiModel
import com.example.myhome.presentation.features.common.models.TypeOfServiceUiModel
import com.example.myhome.presentation.state.DataStateManager
import com.example.myhome.presentation.utils.managers.DatePickersManager
import com.example.myhome.presentation.utils.managers.SelectorManager
import com.example.myhome.presentation.utils.pickers.ImagePicker

abstract class BaseAppealAddView : Fragment() {
    protected lateinit var layoutItemsBinding: AppealAddViewItemsBinding
    private lateinit var datePickersBinding: DatePickersViewBinding
    protected lateinit var dataStateBinding: DataStateBinding

    val viewModel by viewModels<BaseAppealAddViewModel>()

    lateinit var apartmentManager: SelectorManager<ApartmentExtendedUiModel>
    lateinit var typeOfServiceManager: SelectorManager<TypeOfServiceUiModel>

    lateinit var datePickersManager: DatePickersManager
    private lateinit var factoryNumberValidator: com.example.myhome.presentation.utils.InputValidator

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
        viewModel.getState.observe(viewLifecycleOwner) { state ->
            dataStateManager.observeGetState(state)
        }
        viewModel.addState.observe(viewLifecycleOwner) { state ->
            dataStateManager.observeAddState(state)
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
            viewModel.addMeter(it)
        }
    }

    private fun setupValidator() {
        factoryNumberValidator = com.example.myhome.presentation.utils.InputValidator(
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
