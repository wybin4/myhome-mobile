package com.example.myhome.presentation.features.charge.pay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.presentation.BaseDigitPickerView
import com.example.myhome.presentation.features.charge.PaymentHandler
import com.example.myhome.presentation.features.charge.PaymentPicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChargePayView : BaseDigitPickerView() {
    override val viewModel by viewModels<ChargePayViewModel>()

    override val digitPicker = PaymentPicker()
    private lateinit var paymentHandler: PaymentHandler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        paymentHandler = PaymentHandler(requireActivity())

        viewModel.chargeParcelable = requireArguments().getParcelable("charge")!!
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun addNewValue(newValue: Double) {
        viewModel.chargeParcelable.apply {
            paymentHandler.handlePayment(newValue, id, managementCompanyCheckingAccount)
        }
        findNavController().popBackStack()
    }

    override fun setNewValue(new: Double, prev: Double) {
        val rest = digitPicker.calcResult(new, prev)
        binding.leftText.text = rest
        binding.btnNext.isEnabled = new > 0
    }
}
