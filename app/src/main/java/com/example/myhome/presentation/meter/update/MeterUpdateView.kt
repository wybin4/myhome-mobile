package com.example.myhome.presentation.meter.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.databinding.MeterUpdateViewBinding
import com.example.myhome.presentation.appeal.add.verify.BaseAppealVerifyView
import com.example.myhome.utils.managers.state.data.DataStateManager
import com.example.myhome.utils.pickers.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeterUpdateView : BaseAppealVerifyView() {
//    private var _binding: MeterUpdateViewBinding? = null
//    private val binding get() = _binding!!
//    private lateinit var dataStateBinding: DataStateBinding
//
//    private lateinit var datePickersBinding: DatePickersViewBinding
//
//    private val viewModel by viewModels<MeterUpdateViewModel>()
//
//    private lateinit var datePickersManager: DatePickersManager
//
//    private lateinit var imagePicker: ImagePicker
//
//    private lateinit var dataStateManager: DataStateManager
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = MeterUpdateViewBinding.inflate(inflater, container, false)
//        binding.lifecycleOwner = this
//        binding.viewModel = viewModel
//
//        viewModel.meterParcelable = requireArguments().getParcelable("meter")!!
//
//        setupImagePicker()
//        setupDatePickers()
//        setupDateManager(inflater, container)
//
//        binding.nextButton.setOnClickListener { nextClick() }
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        observeResourceState()
//    }
//
//    private fun setupDateManager(inflater: LayoutInflater, container: ViewGroup?) {
//        dataStateBinding = DataStateBinding.inflate(inflater, container, false)
//        dataStateManager = DataStateManager(
//            requireActivity(), dataStateBinding,
//            "Обращение добавлено", "Благодарим за обращение! Ваш запрос на поверку счетчика будет обработан в кратчайшие сроки.",
//            "Ваш запрос на поверку счетчика находится в процессе обработки"
//        ) {
//            findNavController().popBackStack()
//        }
//    }
//
//    private fun setupDatePickers() {
//        datePickersBinding = binding.datePickersLayout
//        datePickersManager = DatePickersManager(
//            datePickersBinding, requireActivity(),
//            viewModel::selectVerifiedAt, viewModel::selectIssuedAt
//        )
//    }
//
//    private fun observeResourceState() {
//        viewModel.dataState.observe(viewLifecycleOwner) { resource ->
//            dataStateManager.observeGetState(resource)
//        }
//        viewModel.dataAddState.observe(viewLifecycleOwner) { resource ->
//            dataStateManager.observeAddState(resource)
//        }
//    }
//
//    private fun setupImagePicker() {
//        imagePicker = ImagePicker(this) {
//            viewModel.selectAttachment = it
//            viewModel.updateMeter()
//        }
//    }
//
//    private fun nextClick() {
//        val isDatePickersValid = datePickersManager.isDatePickersValid()
//        if (isDatePickersValid) {
//            imagePicker.checkStoragePermission()
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }

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
            viewModel.selectAttachment = it
            viewModel.updateMeter()
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
