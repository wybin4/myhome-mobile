package com.example.myhome.presentation.meter.update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.databinding.DatePickersViewBinding
import com.example.myhome.databinding.MeterUpdateViewBinding
import com.example.myhome.utils.managers.state.data.DataStateManager
import com.example.myhome.utils.managers.DatePickersManager
import com.example.myhome.utils.pickers.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeterUpdateView : Fragment() {
    private var _binding: MeterUpdateViewBinding? = null
    private val binding get() = _binding!!

    private var _dataStateBinding: DataStateBinding? = null
    private val dataStateBinding get() = _dataStateBinding!!

    private lateinit var datePickersBinding: DatePickersViewBinding

    private val viewModel by viewModels<MeterUpdateViewModel>()

    private lateinit var datePickersManager: DatePickersManager

    private lateinit var imagePicker: ImagePicker

    private lateinit var dataAddStateManager: DataStateManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MeterUpdateViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.meterParcelable = requireArguments().getParcelable("meter")!!

        setupImagePicker()

        datePickersBinding = binding.datePickersLayout
        datePickersManager = DatePickersManager(
            datePickersBinding, requireActivity(),
            viewModel::selectVerifiedAt, viewModel::selectIssuedAt
        )

        _dataStateBinding = DataStateBinding.inflate(inflater, container, false)
        dataAddStateManager = DataStateManager(
            requireActivity(), dataStateBinding,
            "Обращение добавлено", "Благодарим за обращение! Оно будет обработано в кратчайшие сроки"
            ) {
            findNavController().popBackStack()
        }

        binding.nextButton.setOnClickListener { nextClick() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeResourceState()
    }

    private fun observeResourceState() {
        viewModel.dataState.observe(viewLifecycleOwner) { resource ->
            dataAddStateManager.observeGetState(resource)
        }
        viewModel.dataAddState.observe(viewLifecycleOwner) { resource ->
            dataAddStateManager.observeAddState(resource)
        }
    }

    private fun setupImagePicker() {
        imagePicker = ImagePicker(this) {
            viewModel.selectAttachment = it
            viewModel.updateMeter()
        }
    }

    private fun nextClick() {
        val isDatePickersValid = datePickersManager.isDatePickersValid()
        if (isDatePickersValid) {
            imagePicker.checkStoragePermission()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
