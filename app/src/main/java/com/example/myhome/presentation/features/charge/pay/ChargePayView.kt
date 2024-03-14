package com.example.myhome.presentation.features.charge.pay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.presentation.BaseDigitPickerView
import com.example.myhome.presentation.features.charge.PaymentPicker
import com.example.myhome.presentation.state.data.add.DataAddStateManagerWrapper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChargePayView : BaseDigitPickerView() {
    override val viewModel by viewModels<ChargePayViewModel>()

    override val digitPicker = PaymentPicker()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.сhargeParcelable = requireArguments().getParcelable("charge")!!
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun setupDateManager(inflater: LayoutInflater, container: ViewGroup?) {
        super.setupDateManager(inflater, container)
        dataAddStateManager = DataAddStateManagerWrapper(
            requireActivity(), dataAddStateBinding,
            "Спасибо за оплату", "Ваш платеж находится в обработке",
            "Платеж получен и находится в очереди на обработку"
        ) {
            val navController = findNavController()
//            if (viewModel.dataAddState.value is AddResource.Success) {
//                navController.previousBackStackEntry?.savedStateHandle?.set("current_reading", getNewValue())
//            }
            navController.popBackStack()
        }
    }

    override fun setNewValue(new: Double, prev: Double) {
        val rest = digitPicker.calcResult(new, prev)
        binding.leftText.text = rest
        binding.btnNext.isEnabled = new > 0
    }
}
