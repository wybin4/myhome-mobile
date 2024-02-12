package com.example.myhome.presentation.meter.add

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.common.models.ApartmentGetModel
import com.example.myhome.common.models.TypeOfServiceGetModel
import com.example.myhome.databinding.MeterAddViewBinding
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
    ): View {
        _binding = MeterAddViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupDatePickers()
        setupValidators()
        setupSelectors()

        binding.nextButton.setOnClickListener { nextClick() }

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

//    private fun nextClick() {
//        val isFactoryNumberValid = factoryNumberValidator.validate(viewModel.selectedFactoryNumber)
//        val isApartmentValid = apartmentManager.validate()
//        val isTypeOfServiceValid = typeOfServiceManager.validate()
//        val isVerifiedAtValid = verifiedAtPicker.validate()
//        val isIssuedAtValid = issuedAtPicker.validate()
//
//        if (
//            isFactoryNumberValid &&
//            isApartmentValid && isTypeOfServiceValid
//            && isVerifiedAtValid && isIssuedAtValid
//        ) {
//            viewModel.addMeter()
//        }
//    }

    private fun nextClick() {
        if (
            ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
        )
            {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
            ), PERMISSION_REQUEST_CODE)
        } else {
            openImagePicker()
        }
    }

    private val takePictureLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            // Обработка результата съемки фото
//            val data: Intent? = result.data
//            val imageBitmap = data?.extras?.get("data") as Bitmap
//            binding.imageView.setImageBitmap(imageBitmap)
            // Далее можно использовать полученное изображение
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        takePictureLauncher.launch(intent)
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
