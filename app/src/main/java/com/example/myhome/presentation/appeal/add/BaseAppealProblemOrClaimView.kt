package com.example.myhome.presentation.appeal.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myhome.common.models.ApartmentGetModel
import com.example.myhome.databinding.AppealProblemOrClaimViewBinding
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.utils.InputValidator
import com.example.myhome.utils.managers.SelectorManager
import com.example.myhome.utils.managers.state.data.DataStateManager
import com.example.myhome.utils.models.Resource

abstract class BaseAppealProblemOrClaimView : Fragment() {
    private var _binding: AppealProblemOrClaimViewBinding? = null
    private val binding get() = _binding!!
    protected lateinit var dataStateBinding: DataStateBinding

    protected abstract val viewModel: BaseAppealProblemOrClaimViewModel

    private lateinit var textValidator: InputValidator
    private lateinit var apartmentManager: SelectorManager<ApartmentGetModel>

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
        textValidator = InputValidator(
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
