package com.example.myhome.presentation.features.meter.scan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.databinding.DataStateBinding
import com.example.myhome.databinding.MeterScanViewBinding
import com.example.myhome.presentation.models.AddResource
import com.example.myhome.presentation.state.data.add.DataAddStateManagerWrapper
import com.example.myhome.presentation.utils.pickers.DigitPicker
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeterScanView : Fragment() {
    private var _binding: MeterScanViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MeterScanViewModel>()
    
    private val digitPicker = DigitPicker()

    private lateinit var dataAddStateBinding: DataStateBinding
    private lateinit var dataAddStateManager: DataAddStateManagerWrapper

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
            viewModel.addMeterReading(getCurrentReading())
        }
    }

    private fun getCurrentReading(): Double {
        val newValString = binding.currentReading.text.toString()
        return digitPicker.stringToDouble(newValString)
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
                navController.previousBackStackEntry?.savedStateHandle?.set("current_reading", getCurrentReading())
            }
            navController.popBackStack()
        }
    }

    private val digitClickListener = View.OnClickListener { view ->
        var buttonText = (view as Button).text.toString()
        var prevVal = binding.currentReading.text.toString()
        binding.currentReading.text = digitPicker.addDigit(buttonText, prevVal)

        setConsumption(digitPicker.stringToDouble(
            binding.currentReading.text.toString()),
            viewModel.meterParcelable.previousReading
        )
    }

    private fun setConsumption(new: Double, prev: Double) {
        val consumption = digitPicker.calcConsumption(new, prev)
        binding.consumption.text = consumption
        binding.btnNext.isEnabled = consumption != "0"
    }

    private fun clearDigit(){
        val oldVal = binding.currentReading.text.toString()
        val newVal = digitPicker.clearDigit(oldVal)
        if (newVal != null) {
            binding.currentReading.text = newVal
            setConsumption(
                digitPicker.stringToDouble(newVal),
                viewModel.meterParcelable.previousReading
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
