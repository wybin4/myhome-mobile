package com.example.myhome.presentation.meter.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myhome.databinding.MeterUpdateViewBinding
import com.example.myhome.utils.pickers.CustomDatePicker
import com.example.myhome.utils.pickers.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeterUpdateView : Fragment() {
    private var _binding: MeterUpdateViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MeterUpdateViewModel>()

    private lateinit var verifiedAtPicker: CustomDatePicker
    private lateinit var issuedAtPicker: CustomDatePicker

    private lateinit var imagePicker: ImagePicker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MeterUpdateViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.meterParcelable = requireArguments().getParcelable("meter")!!

        setupDatePickers()

        setupImagePicker()

        binding.nextButton.setOnClickListener { nextClick() }

        return binding.root
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

    private fun setupImagePicker() {
        imagePicker = ImagePicker(this) {
            viewModel.selectAttachment = it
            viewModel.updateMeter()
        }
    }

    private fun nextClick() {
        val isVerifiedAtValid = verifiedAtPicker.validate()
        val isIssuedAtValid = issuedAtPicker.validate()

        if (isVerifiedAtValid && isIssuedAtValid) {
            imagePicker.checkStoragePermission()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
