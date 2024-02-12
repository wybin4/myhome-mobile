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
import com.example.myhome.presentation.meter.MeterParcelableModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date


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
         adapter = MeterListAdapter(requireActivity()) {

             val meter = MeterParcelableModel(
                 id = it.id,
                 factoryNumber = it.factoryNumber,
                 verifiedAt = it.verifiedAt,
                 issuedAt = it.issuedAt,
                 address = "пер. Соборный 99, кв. 12",
                 currentReading = it.currentReading?.toString() ?: "—",
                 typeOfServiceName = it.typeOfServiceName,
                 unitName = it.unitName
             )


             val bundle = Bundle().apply {
                 putParcelable("meter", meter)
             }

             findNavController().navigate(R.id.action_MeterListView_to_MeterGetView, bundle)

         }
        binding.recyclerView.adapter = adapter
    }

}
