package com.example.myhome.presentation.meter.add

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.MeterAddViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeterAddView : Fragment() {
    private var _binding: MeterAddViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MeterAddViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MeterAddViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
