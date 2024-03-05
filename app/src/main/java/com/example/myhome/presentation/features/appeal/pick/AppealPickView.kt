package com.example.myhome.presentation.features.appeal.pick

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.AppealPickViewBinding
import com.example.myhome.features.appeal.models.AppealType
import com.example.myhome.presentation.features.appeal.AppealPicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppealPickView : Fragment() {
    private var _binding: AppealPickViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<AppealPickViewModel>()

    private lateinit var addPicker: AppealPicker
    private lateinit var verifyPicker: AppealPicker
    private lateinit var problemPicker: AppealPicker
    private lateinit var claimPicker: AppealPicker

    private var isError: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AppealPickViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupPickerButtons()
        binding.nextButton.setOnClickListener { nextClick() }

        return binding.root
    }

    private fun setupPickerButtons() {
        val activity = requireActivity()
        addPicker = AppealPicker(
            activity,
            binding.addPickedLayout, binding.addPickedIcon
        ) { pickerButtonClick(AppealType.AddIndividualMeter) }
        verifyPicker = AppealPicker(
            activity,
            binding.verifyPickedLayout, binding.verifyPickedIcon
        ) { pickerButtonClick(AppealType.VerifyIndividualMeter) }
        problemPicker = AppealPicker(
            activity,
            binding.problemOrQuestionPickedLayout, binding.problemOrQuestionPickedIcon
        ) { pickerButtonClick(AppealType.ProblemOrQuestion) }
        claimPicker = AppealPicker(
            activity,
            binding.claimPickedLayout, binding.claimPickedIcon
        ) { pickerButtonClick(AppealType.Claim) }
    }

    private fun pickerButtonClick(appealType: AppealType) {
        setAllPickerButtonsUnclickedColor()
        viewModel.selected = appealType
    }

    private fun setAllPickerButtonsUnclickedColor() {
        addPicker.setButtonUnclickedColor()
        verifyPicker.setButtonUnclickedColor()
        problemPicker.setButtonUnclickedColor()
        claimPicker.setButtonUnclickedColor()

        if (isError) {
            addPicker.setIconColor()
            verifyPicker.setIconColor()
            problemPicker.setIconColor()
            claimPicker.setIconColor()

            binding.errorText.visibility = View.GONE

            isError = false
        }
    }

    private fun setAllPickerButtonsError() {
        addPicker.setButtonErrorColor()
        verifyPicker.setButtonErrorColor()
        problemPicker.setButtonErrorColor()
        claimPicker.setButtonErrorColor()

        addPicker.setIconErrorColor()
        verifyPicker.setIconErrorColor()
        problemPicker.setIconErrorColor()
        claimPicker.setIconErrorColor()

        binding.errorText.visibility = View.VISIBLE
    }

    private fun nextClick() {
        if (viewModel.selected == null) {
            setAllPickerButtonsError()
            isError = true
        } else {
            isError = false

            when(viewModel.selected) {
                AppealType.AddIndividualMeter -> findNavController().navigate(R.id.action_appealPickView_to_appealAddView)
                AppealType.VerifyIndividualMeter -> findNavController().navigate(R.id.action_appealPickView_to_appealVerifyView)
                AppealType.ProblemOrQuestion -> findNavController().navigate(R.id.action_appealPickView_to_appealProblemView)
                AppealType.Claim -> findNavController().navigate(R.id.action_appealPickView_to_appealClaimView)
                else -> {}
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
