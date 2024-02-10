package com.example.myhome.presentation.meter.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.MeterListViewBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MeterListView : Fragment() {
    private var _binding: MeterListViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MeterListViewModel>()
    private lateinit var adapter: MeterListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MeterListViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupRecyclerView()

        binding.addMeterButton.setOnClickListener {
            findNavController().navigate(R.id.action_MeterListView_to_MeterAddView)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.meterList.observe(viewLifecycleOwner) { meters ->
            adapter.submitList(meters)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        adapter = MeterListAdapter(requireActivity())
        binding.recyclerView.adapter = adapter
    }

}
