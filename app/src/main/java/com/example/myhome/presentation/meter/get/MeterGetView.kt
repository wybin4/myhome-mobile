package com.example.myhome.presentation.meter.get

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.MeterGetViewBinding
import com.example.myhome.databinding.ReadingListItemBinding
import com.example.myhome.presentation.CustomListAdapter
import com.example.myhome.presentation.meter.models.MeterGetToUpdateParcelableModel
import com.example.myhome.presentation.meter.models.MeterListToGetParcelableModel
import com.example.myhome.presentation.meter.models.ReadingUiModel
import dagger.hilt.android.AndroidEntryPoint

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
        binding.verifiedAt.text = viewModel.meterParcelable.formatVerifiedAt()
        binding.issuedAt.text = viewModel.meterParcelable.formatIssuedAt()

        viewModel.fetchReadingList()
        setupRecyclerView()

        binding.updateMeterButton.setOnClickListener {
            val meter = MeterGetToUpdateParcelableModel(
                meterId = viewModel.meterParcelable.id,
                meterName = viewModel.meterParcelable.address + ", " + viewModel.meterParcelable.typeOfServiceName,
                apartmentId = viewModel.meterParcelable.apartmentId
            )
            Log.e("updateMeterButton", meter.toString())

            val bundle = Bundle().apply {
                putParcelable("meter", meter)
            }
            findNavController().navigate(R.id.action_meterGetView_to_meterUpdateView, bundle)
        }
        binding.addReadingButton.setOnClickListener {
//            findNavController().navigate(R.id.action_MeterListView_to_MeterAddView)
        }

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
