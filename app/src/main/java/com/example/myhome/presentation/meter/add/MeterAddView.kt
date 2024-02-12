package com.example.myhome.presentation.meter.add

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myhome.common.models.ApartmentGetModel
import com.example.myhome.common.models.TypeOfServiceGetModel
import com.example.myhome.databinding.MeterAddViewBinding
import com.example.myhome.meter.models.MeterAddModel
import com.example.myhome.presentation.CustomDatePicker
import com.example.myhome.presentation.InputValidator
import com.example.myhome.presentation.SelectorManager
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MeterAddView : Fragment() {
    private var _binding: MeterAddViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MeterAddViewModel>()

    private lateinit var apartmentManager: SelectorManager<ApartmentGetModel>
    private lateinit var typeOfServiceManager: SelectorManager<TypeOfServiceGetModel>

    private lateinit var verifiedAtPicker: CustomDatePicker
    private lateinit var issuedAtPicker: CustomDatePicker

    private lateinit var factoryNumberValidator: InputValidator

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MeterAddViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupDatePickers()
        setupValidators()
        setupSelectors()

        binding.nextButton.setOnClickListener {
            val isFactoryNumberValid = factoryNumberValidator.validate(viewModel.selectedFactoryNumber)
            val isApartmentValid = apartmentManager.validate()
            val isTypeOfServiceValid = typeOfServiceManager.validate()
            val isVerifiedAtValid = verifiedAtPicker.validate()
            val isIssuedAtValid = issuedAtPicker.validate()

            if (
                isFactoryNumberValid &&
                isApartmentValid && isTypeOfServiceValid
                && isVerifiedAtValid && isIssuedAtValid
            ) {
                viewModel.addMeter()
            }
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.apartmentList.observe(viewLifecycleOwner) { apartments ->
            apartmentManager.update(apartments)
        }
        viewModel.typeOfServiceList.observe(viewLifecycleOwner) { typesOfService ->
            typeOfServiceManager.update(typesOfService)
        }
    }

    private fun setupSelectors() {
        apartmentManager = SelectorManager(
            requireActivity(),
            binding.apartmentList, binding.apartmentSelector, "квартиру",
            viewModel::selectedApartmentId
        )

        typeOfServiceManager = SelectorManager(
            requireActivity(),
            binding.typeOfServiceList, binding.typeOfServiceSelector, "тип услуги",
            viewModel::selectedTypeOfServiceId
        )
    }

    private fun setupDatePickers() {
        verifiedAtPicker = CustomDatePicker(
            requireActivity(),
            viewModel::selectVerifiedAt ,binding.verifiedAt, binding.verifiedAtDatePicker,
            "поверки"
        )
        binding.verifiedAt.setOnClickListener {
            verifiedAtPicker.show()
        }

        issuedAtPicker = CustomDatePicker(
            requireActivity(),
            viewModel::selectIssuedAt, binding.issuedAt, binding.issuedAtDatePicker,
            "истечения"
        )
        binding.issuedAt.setOnClickListener {
            issuedAtPicker.show()
        }
    }

    private fun setupValidators() {
        factoryNumberValidator = InputValidator(
            binding.factoryNumberInput,
            { text: String? -> text?.length!! > 0 },
            "Введите заводской номер",
            {
                binding.factoryNumberInput.editText?.doOnTextChanged { _, _, _, _ ->
                    factoryNumberValidator.validate(viewModel.selectedFactoryNumber)
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
