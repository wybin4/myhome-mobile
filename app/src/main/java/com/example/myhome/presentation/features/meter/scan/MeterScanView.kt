package com.example.myhome.presentation.features.meter.scan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.presentation.BaseDigitPickerView
import com.example.myhome.presentation.features.meter.ReadingPicker
import com.example.myhome.presentation.models.AddResource
import com.example.myhome.presentation.state.data.add.DataAddStateManagerWrapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeterScanView : BaseDigitPickerView() {
    override val viewModel by viewModels<MeterScanViewModel>()

    private lateinit var dataAddStateBinding: DataStateBinding
    private lateinit var dataAddStateManager: DataAddStateManagerWrapper

    override val digitPicker = ReadingPicker()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.meterParcelable = requireArguments().getParcelable("meter")!!
        setupDateManager(inflater, container)

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.dataAddState.observe(viewLifecycleOwner) { resource ->
            dataAddStateManager.observeState(resource)
        }
    }

    override fun addNewValue(newValue: Double) {
        viewModel.addNewValue(newValue)
    }

    private fun setupDateManager(inflater: LayoutInflater, container: ViewGroup?) {
        dataAddStateBinding = DataStateBinding.inflate(inflater, container, false)
        dataAddStateManager = DataAddStateManagerWrapper(
            requireActivity(), dataAddStateBinding,
            "Показание добавлено", "Мы высоко ценим вашу оперативность!",
            "Показание получено и находится в очереди на добавление"
        ) {
            val navController = findNavController()
            if (viewModel.dataAddState.value is AddResource.Success) {
                navController.previousBackStackEntry?.savedStateHandle?.set("current_reading", getNewValue())
            }
            navController.popBackStack()
        }
    }

    override fun setNewValue(new: Double, prev: Double) {
        val consumption = digitPicker.calcResult(new, prev)
        binding.leftText.text = consumption
        binding.btnNext.isEnabled = consumption != "0"
    }

}
