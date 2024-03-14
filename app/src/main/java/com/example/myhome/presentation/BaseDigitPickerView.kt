package com.example.myhome.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.myhome.databinding.DigitPickerViewBinding
import com.example.myhome.presentation.utils.pickers.DigitPicker

abstract class BaseDigitPickerView : Fragment() {
    private var _binding: DigitPickerViewBinding? = null
    val binding get() = _binding!!
    protected abstract val viewModel: BaseDigitPickerViewModel

    protected abstract val digitPicker: DigitPicker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DigitPickerViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupDigits()
        binding.apply {
            btnDel.setOnClickListener{ clearDigit() }
            btnNext.setOnClickListener { nextClick() }
        }

        return binding.root
    }

    abstract fun addNewValue(newValue: Double)

    private fun nextClick() {
        if (binding.btnNext.isEnabled) {
            addNewValue(getNewValue())
        }
    }

    private val digitClickListener = View.OnClickListener { view ->
        var buttonText = (view as Button).text.toString()
        var prevVal = binding.enteredText.text.toString()
        binding.enteredText.text = digitPicker.addDigit(buttonText, prevVal)

        setNewValue(digitPicker.stringToDouble(binding.enteredText.text.toString()), viewModel.getPrevValue())
    }

    protected fun getNewValue(): Double {
        val newValString = binding.enteredText.text.toString()
        return digitPicker.stringToDouble(newValString)
    }

    protected abstract fun setNewValue(new: Double, prev: Double)

    private fun clearDigit(){
        val oldVal = binding.enteredText.text.toString()
        val newVal = digitPicker.clearDigit(oldVal)
        if (newVal != null) {
            binding.enteredText.text = newVal
            setNewValue(
                digitPicker.stringToDouble(newVal),
                viewModel.getPrevValue()
            )
        }
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
