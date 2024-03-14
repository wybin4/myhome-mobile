package com.example.myhome.presentation.features.meter.scan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.presentation.BaseDigitPickerView
import com.example.myhome.presentation.features.meter.ReadingPicker
import com.example.myhome.presentation.models.AddResource
import com.example.myhome.presentation.state.data.add.DataAddStateManagerWrapper
import com.example.myhome.presentation.utils.pickers.DigitPicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeterScanView : BaseDigitPickerView() {
    override val viewModel by viewModels<MeterScanViewModel>()

    override val digitPicker = ReadingPicker()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.meterParcelable = requireArguments().getParcelable("meter")!!
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun setupDateManager(inflater: LayoutInflater, container: ViewGroup?) {
        super.setupDateManager(inflater, container)
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
