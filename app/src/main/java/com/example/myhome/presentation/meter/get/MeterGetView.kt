package com.example.myhome.presentation.meter.get

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.MeterGetViewBinding
import com.example.myhome.databinding.MeterListItemLoadingBinding
import com.example.myhome.databinding.ReadingListItemBinding
import com.example.myhome.databinding.ReadingListItemLoadingBinding
import com.example.myhome.utils.ConstantsUi
import com.example.myhome.utils.adapters.CustomListAdapter
import com.example.myhome.utils.adapters.InfiniteListAdapter
import com.example.myhome.utils.models.ReadingUiModel
import com.example.myhome.utils.models.Resource
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeterGetView : Fragment() {
    private var _binding: MeterGetViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MeterGetViewModel>()
    private lateinit var readingListAdapter: CustomListAdapter<ReadingUiModel, ReadingListItemBinding>
    private lateinit var readingInfiniteListAdapter: InfiniteListAdapter<String, ReadingListItemLoadingBinding>

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
        setupInfiniteRecyclerView()
        setupActionButtons()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeResourceStates()
        observeList()
    }

    private fun observeResourceStates() {
        viewModel.readingListState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> showLoadingState()
                is Resource.Success -> showSuccessState()
                is Resource.Empty -> showEmptyState()
                is Resource.Error -> showErrorState()
            }
        }
    }

    private fun observeList() {
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

    private fun showLoadingState() {
        binding.readingInfiniteRecyclerView.visibility = View.VISIBLE
        binding.readingRecyclerView.visibility = View.GONE
    }

    private fun showSuccessState() {
        binding.readingRecyclerView.visibility = View.VISIBLE
        binding.readingInfiniteRecyclerView.visibility = View.GONE
    }

    private fun showEmptyState() {
        TODO("Доделать")
    }

    private fun showErrorState() {
        TODO("Доделать")
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchReadingList()
    }

    private fun setupActionButtons() {
        binding.updateMeterButton.setOnClickListener {
            val bundle = viewModel.mapMeterGetToUpdateParcel(viewModel.meterParcelable).toBundle()
            findNavController().navigate(R.id.action_meterGetView_to_meterUpdateView, bundle)
        }
        if (viewModel.meterParcelable.currentReading == null) {
            binding.addReadingButton.setOnClickListener {
                val prevReading = viewModel.readingList.value?.firstOrNull()?.reading ?: 0.0
                val bundle = viewModel.mapMeterGetToScanParcel(viewModel.meterParcelable, prevReading).toBundle()
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

        binding.readingRecyclerView.adapter = readingListAdapter
    }

    private fun setupInfiniteRecyclerView() {
        readingInfiniteListAdapter = InfiniteListAdapter(
            itemList = List(ConstantsUi.VERTICAL_LOADING_RECYCLER_VIEW_SIZE) { "" },
            itemBindingInflater = { inflater, parent, attachToParent ->
                ReadingListItemLoadingBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.item = item
            }
        )
        binding.readingInfiniteRecyclerView.adapter = readingInfiniteListAdapter
        binding.readingInfiniteRecyclerView.hasFixedSize()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
