package com.example.myhome.presentation.meter.get

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.MeterGetViewBinding
import com.example.myhome.databinding.ReadingListItemBinding
import com.example.myhome.presentation.CustomListAdapter
import com.example.myhome.presentation.models.MeterGetToScanParcelableModel
import com.example.myhome.presentation.models.MeterGetToUpdateParcelableModel
import com.example.myhome.presentation.models.ReadingUiModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MeterGetView : Fragment() {
    private var _binding: MeterGetViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MeterGetViewModel>()
    private lateinit var readingListAdapter: CustomListAdapter<ReadingUiModel, ReadingListItemBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MeterGetViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.meterParcelable = requireArguments().getParcelable("meter")!!
        if (viewModel.meterParcelable.currentReading == null) {
            binding.unitName.visibility = View.GONE
        }
        binding.verifiedAt.text = viewModel.meterParcelable.formatVerifiedAt()

        setupRecyclerView()
        setupActionButtons()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.readingList.observe(viewLifecycleOwner) { readings ->
            val list = readings.map { item ->
                ReadingUiModel(
                    id = item.id,
                    reading = item.reading,
                    consumption = item.consumption,
                    readAt = item.readAt,
                    unitName = viewModel.meterParcelable.unitName
                )
            }
            readingListAdapter.submitList(list)
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchReadingList()
    }

    private fun setupActionButtons() {
        binding.updateMeterButton.setOnClickListener {
            val meter = MeterGetToUpdateParcelableModel(
                meterId = viewModel.meterParcelable.id,
                meterName = viewModel.meterParcelable.address + ", " + viewModel.meterParcelable.typeOfServiceName,
                apartmentId = viewModel.meterParcelable.apartmentId
            )

            val bundle = Bundle().apply {
                putParcelable("meter", meter)
            }
            findNavController().navigate(R.id.action_meterGetView_to_meterUpdateView, bundle)
        }
        if (viewModel.meterParcelable.currentReading == null) {
            binding.addReadingButton.setOnClickListener {
                val prevReading = viewModel.readingList.value?.firstOrNull()?.reading?.toDouble() ?: 0.0

                val meter = MeterGetToScanParcelableModel(
                    meterId = viewModel.meterParcelable.id,
                    address = viewModel.meterParcelable.address,
                    previousReading = prevReading,
                    typeOfServiceName = viewModel.meterParcelable.typeOfServiceName,
                    typeOfServiceEngName = viewModel.meterParcelable.typeOfServiceEngName,
                    unitName = viewModel.meterParcelable.unitName
                )

                val bundle = Bundle().apply {
                    putParcelable("meter", meter)
                }
                findNavController().navigate(R.id.action_meterGetView_to_meterScanView, bundle)
            }
        } else {
            binding.addReadingButton.visibility = View.GONE
        }
    }

    private fun setupRecyclerView() {
        readingListAdapter = CustomListAdapter(
            context = requireActivity(),
            itemBindingInflater = { inflater, parent, attachToParent ->
                ReadingListItemBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.reading = item
            },
            onItemClick = null
        )

        binding.recyclerView.adapter = readingListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
