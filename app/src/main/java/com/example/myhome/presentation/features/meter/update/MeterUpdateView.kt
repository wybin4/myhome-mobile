package com.example.myhome.presentation.features.meter.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.databinding.MeterUpdateViewBinding
import com.example.myhome.presentation.features.appeal.add.verify.BaseAppealVerifyView
import com.example.myhome.presentation.state.DataStateManager
import com.example.myhome.presentation.utils.pickers.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeterUpdateView : BaseAppealVerifyView() {
    private var _binding: MeterUpdateViewBinding? = null
    private val binding get() = _binding!!
    
    override val viewModel by viewModels<MeterUpdateViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MeterUpdateViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.meterParcelable = requireArguments().getParcelable("meter")!!

        super.onCreateView(inflater, container, savedInstanceState)
        binding.nextButton.setOnClickListener { nextClick() }

        return binding.root
    }

    private fun nextClick() {
        if (validate()) {
            imagePicker.checkStoragePermission()
        }
    }

    override fun setupImagePicker() {
        imagePicker = ImagePicker(this) {
            viewModel.updateMeter(it)
        }
    }

    override fun setupDatePickers() {
        datePickersBinding = binding.datePickersLayout
        super.setupDatePickers()
    }

    override fun setupDateManager(inflater: LayoutInflater, container: ViewGroup?) {
        super.setupDateManager(inflater, container)
        dataStateManager = DataStateManager(
            requireActivity(), dataStateBinding,
            "Обращение добавлено", "Благодарим за обращение! Ваш запрос на поверку счетчика будет обработан в кратчайшие сроки",
            "Ваш запрос на поверку счетчика находится в процессе обработки"
        ) {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
