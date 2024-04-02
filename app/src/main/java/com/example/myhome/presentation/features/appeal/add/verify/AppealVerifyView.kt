package com.example.myhome.presentation.features.appeal.add.verify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.AppealVerifyViewBinding
import com.example.myhome.presentation.features.meter.MeterExtendedUiModel
import com.example.myhome.presentation.state.DataStateManager
import com.example.myhome.presentation.utils.managers.SelectorManager
import com.example.myhome.presentation.utils.pickers.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppealVerifyView : BaseAppealVerifyView() {
    private var _binding: AppealVerifyViewBinding? = null
    private val binding get() = _binding!!

    override val viewModel by viewModels<AppealVerifyViewModel>()

    private lateinit var meterManager: SelectorManager<MeterExtendedUiModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AppealVerifyViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupSelector()

        super.onCreateView(inflater, container, savedInstanceState)
        binding.nextButton.setOnClickListener { nextClick() }

        return binding.root
    }

    private fun nextClick() {
        if (validate()) {
            imagePicker.checkStoragePermission()
        }
    }

    private fun setupSelector() {
        meterManager = SelectorManager(
            requireActivity(),
            binding.meterList, binding.meterSelector, "счетчик",
            viewModel::selectedMeterId
        )
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

    override fun observeResourceState() {
        super.observeResourceState()
        viewModel.meterList.observe(viewLifecycleOwner) { apartments ->
            meterManager.update(apartments)
        }
    }

    override fun setupDateManager(inflater: LayoutInflater, container: ViewGroup?) {
        super.setupDateManager(inflater, container)
        dataStateManager = DataStateManager(
            requireActivity(), dataStateBinding,
            "Обращение добавлено", "Благодарим за обращение! Оно будет обработано в кратчайшие сроки",
            "Ваш запрос на создание обращения в процессе обработки"
        ) {
            findNavController().navigate(R.id.action_appealVerifyView_to_appealListView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
