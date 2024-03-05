package com.example.myhome.presentation.features.appeal.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import com.example.myhome.databinding.AppealProblemOrClaimViewBinding
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.presentation.features.common.models.ApartmentExtendedUiModel
import com.example.myhome.presentation.state.data.DataStateManager
import com.example.myhome.presentation.utils.managers.SelectorManager

abstract class BaseAppealProblemOrClaimView : Fragment() {
    private var _binding: AppealProblemOrClaimViewBinding? = null
    private val binding get() = _binding!!
    protected lateinit var dataStateBinding: DataStateBinding

    protected abstract val viewModel: BaseAppealProblemOrClaimViewModel

    private lateinit var textValidator: com.example.myhome.presentation.utils.InputValidator
    private lateinit var apartmentManager: SelectorManager<ApartmentExtendedUiModel>

    protected lateinit var dataStateManager: DataStateManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AppealProblemOrClaimViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupValidator()
        setupSelector()
        setupDateManager(inflater, container)

        binding.nextButton.setOnClickListener { nextClick() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeResourceState()
        viewModel.apartmentList.observe(viewLifecycleOwner) { apartments ->
            apartmentManager.update(apartments)
        }
    }

    private fun observeResourceState() {
        viewModel.dataAddState.observe(viewLifecycleOwner) { resource ->
            dataStateManager.observeAddState(resource)
        }
    }

    protected open fun setupDateManager(inflater: LayoutInflater, container: ViewGroup?) {
        dataStateBinding = DataStateBinding.inflate(inflater, container, false)
    }

    private fun setupValidator() {
        textValidator = com.example.myhome.presentation.utils.InputValidator(
            binding.textInput,
            { text: String? -> text?.length!! in 1..1000 },
            "Введите текст",
            {
                binding.textInput.editText?.doOnTextChanged { _, _, _, _ ->
                    textValidator.validate(viewModel.selectedText)
                }
            }
        )
    }

    private fun setupSelector() {
        apartmentManager = SelectorManager(
            requireActivity(),
            binding.apartmentList, binding.apartmentSelector, "квартиру",
            viewModel::selectedApartmentId
        )
    }

    private fun nextClick() {
        val isTextValid = textValidator.validate(viewModel.selectedText)
        if (isTextValid) {
            viewModel.claim()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
