package com.example.myhome.presentation.meter.scan

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myhome.databinding.MeterScanViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeterScanView : Fragment() {
    private var _binding: MeterScanViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MeterScanViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MeterScanViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.meterParcelable = requireArguments().getParcelable("meter")!!

        setupDigits()
        binding.apply {
            btnDel.setOnClickListener{ clearDigit() }
            btnNext.setOnClickListener { nextClick() }
        }

        return binding.root
    }

    private fun nextClick() {
        if (binding.btnNext.isEnabled) {
            Log.e("nextClick", "nextClick")
        }
    }

    private val digitClickListener = View.OnClickListener { view ->
        var buttonText = (view as Button).text.toString()
        var prevVal = binding.currentReading.text.toString()
        if (prevVal == "0" && buttonText != ",") {
            prevVal = ""
        }

        val commaCount = prevVal.count { it == ',' }
        if (commaCount > 0 && buttonText == ",") {
            buttonText = ""
        }
        binding.currentReading.text = prevVal + buttonText

        val newValString = binding.currentReading.text.toString()
        val newValFloat = newValString.replace(',', '.').toFloatOrNull() ?: 0f
        calcConsumption(newValFloat, viewModel.meterParcelable.previousReading)
    }

    private fun calcConsumption(new: Float, prev: Float){
        if (new > prev) {
            val newConsumption = new - prev
            val formattedValue = String.format("%.3f", newConsumption)
            binding.consumption.text = formattedValue

            binding.btnNext.isEnabled = true // !!!!!
        } else {
            binding.consumption.text = "0"
            binding.btnNext.isEnabled = false // !!!!!
        }
    }

    private fun clearDigit(){
        var prevVal = binding.currentReading.text.toString()
        if (prevVal.length > 1) {
            prevVal = prevVal.slice(0 until prevVal.length - 1)
            binding.currentReading.text = prevVal
        } else if (prevVal.length == 1 && prevVal != "0") {
            binding.currentReading.text = "0"
        }

        val newValString = binding.currentReading.text.toString()
        val newValFloat = newValString.replace(',', '.').toFloatOrNull() ?: 0f
        calcConsumption(newValFloat, viewModel.meterParcelable.previousReading)
    }

    private fun setupDigits() {
        binding.apply {
            btn0.setOnClickListener(digitClickListener)
            btn1.setOnClickListener(digitClickListener)
            btn2.setOnClickListener(digitClickListener)
            btn3.setOnClickListener(digitClickListener)
            btn4.setOnClickListener(digitClickListener)
            btn5.setOnClickListener(digitClickListener)
            btn6.setOnClickListener(digitClickListener)
            btn7.setOnClickListener(digitClickListener)
            btn8.setOnClickListener(digitClickListener)
            btn9.setOnClickListener(digitClickListener)
            btnComma.setOnClickListener(digitClickListener)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
