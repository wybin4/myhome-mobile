package com.example.myhome.presentation.meter.get

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myhome.databinding.MeterGetViewBinding
import com.example.myhome.presentation.meter.MeterParcelableModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Date


@AndroidEntryPoint
class MeterGetView : Fragment() {
    private var _binding: MeterGetViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MeterGetViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MeterGetViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        val meter = requireArguments().getParcelable<MeterParcelableModel>("meter")

        if (meter != null) {
            binding.currentReading.text = meter.currentReading
            binding.unitName.text = meter.unitName
            binding.typeOfServiceName.text = meter.typeOfServiceName
            binding.factoryNumber.text = meter.factoryNumber
            binding.address.text = meter.address
            binding.verifiedAt.text = dateToString(meter.verifiedAt)
            binding.issuedAt.text = dateToString(meter.issuedAt)
        } else{
            TODO("Ошибка?")
        }


        return binding.root
    }

    private fun dateToString(date: Date): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        return dateFormat.format(date)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
