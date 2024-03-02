package com.example.myhome.presentation.appeal.add.verify

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.AppealVerifyViewBinding
import com.example.myhome.databinding.DatePickersViewBinding
import com.example.myhome.utils.managers.DatePickersManager
import com.example.myhome.utils.managers.SelectorManager
import com.example.myhome.utils.models.MeterListItemUiModel
import com.example.myhome.utils.models.Resource
import com.example.myhome.utils.pickers.ImagePicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppealVerifyView : Fragment() {
    private var _binding: AppealVerifyViewBinding? = null
    private val binding get() = _binding!!
    private lateinit var datePickersBinding: DatePickersViewBinding

    private val viewModel by viewModels<AppealVerifyViewModel>()

    private lateinit var meterManager: SelectorManager<MeterListItemUiModel>
    private lateinit var datePickersManager: DatePickersManager
    private lateinit var imagePicker: ImagePicker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AppealVerifyViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupSelector()
        setupImagePicker()

        datePickersBinding = binding.datePickersLayout
        datePickersManager = DatePickersManager(
            datePickersBinding, requireActivity(),
            viewModel::selectVerifiedAt, viewModel::selectIssuedAt
        )

        binding.nextButton.setOnClickListener { nextClick() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.meterList.observe(viewLifecycleOwner) { meters ->
            meterManager.update(meters)
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

    private fun showSuccessState() {
        findNavController().navigate(R.id.action_appealVerifyView_to_appealListView)
    }

    private fun showErrorState() {
        TODO("Доделать")
    }

    private fun setupSelector() {
        meterManager = SelectorManager(
            requireActivity(),
            binding.meterList, binding.meterSelector, "счетчик",
            viewModel::selectedMeterId
        )
    }

    private fun setupImagePicker() {
        imagePicker = ImagePicker(this) {
            viewModel.selectAttachment = it
            viewModel.updateMeter()
        }
    }

    private fun nextClick() {
        val isMeterValid = meterManager.validate()
        val isDatePickersValid = datePickersManager.isDatePickersValid()
        if (isMeterValid && isDatePickersValid) {
            imagePicker.checkStoragePermission()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
