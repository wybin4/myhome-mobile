package com.example.myhome.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.databinding.DigitPickerViewBinding
import com.example.myhome.presentation.state.data.add.DataAddStateManagerWrapper
import com.example.myhome.presentation.features.meter.ReadingPicker
import com.example.myhome.presentation.utils.pickers.DigitPicker

abstract class BaseDigitPickerView : Fragment() {
    private var _binding: DigitPickerViewBinding? = null
    val binding get() = _binding!!
    protected abstract val viewModel: BaseDigitPickerViewModel

    protected abstract val digitPicker: DigitPicker

    protected lateinit var dataAddStateBinding: DataStateBinding
    protected lateinit var dataAddStateManager: DataAddStateManagerWrapper

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

        setupDateManager(inflater, container)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.dataAddState.observe(viewLifecycleOwner) { resource ->
            dataAddStateManager.observeState(resource)
        }
    }

    private fun nextClick() {
        if (binding.btnNext.isEnabled) {
            viewModel.addNewValue(getNewValue())
        }
    }
    protected open fun setupDateManager(inflater: LayoutInflater, container: ViewGroup?) {
        dataAddStateBinding = DataStateBinding.inflate(inflater, container, false)
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
