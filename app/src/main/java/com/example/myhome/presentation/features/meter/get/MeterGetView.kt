package com.example.myhome.presentation.features.meter.get

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.MeterGetViewBinding
import com.example.myhome.databinding.ReadingListItemBinding
import com.example.myhome.databinding.ReadingListItemLoadingBinding
import com.example.myhome.presentation.ConstantsUi
import com.example.myhome.presentation.features.meter.ReadingUiModel
import com.example.myhome.presentation.state.list.ListState
import com.example.myhome.presentation.utils.adapters.AdaptiveIntListAdapter
import com.example.myhome.presentation.utils.adapters.InfiniteListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeterGetView : Fragment() {
    private var _binding: MeterGetViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MeterGetViewModel>()
    private lateinit var readingListAdapter: AdaptiveIntListAdapter<ReadingUiModel, ReadingListItemBinding>
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

        observeCurrentReading()

        setupRecyclerView()
        setupInfiniteRecyclerView()
        setupActionButtons()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeResourceState()
        observeList()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchReadingList()
    }

    private fun observeResourceState() {
        viewModel.readingListState.observe(viewLifecycleOwner) {
            updateViewState(it)
        }
    }

    private fun observeCurrentReading() {
        val navController = requireActivity()
            .supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main)
            ?.findNavController()

        navController?.currentBackStackEntry?.savedStateHandle?.getLiveData<Double>("current_reading")
            ?.observe(viewLifecycleOwner) { reading ->
                reading?.let {
                    if (it > 0) {
                        viewModel.meterParcelable.currentReading = it
                        binding.currentReading.text = it.toString()
                        binding.addReadingButton.visibility = View.GONE
                    }
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

    private fun updateViewState(state: ListState) {
        binding.apply {
            readingInfiniteRecyclerView.visibility = state.loadingVisibility
            readingRecyclerView.visibility = state.successVisibility
            readingOnEmpty.visibility = state.emptyVisibility
            readingOnError.visibility = state.errorVisibility
        }
    }

    private fun setupActionButtons() {
        binding.updateMeterButton.setOnClickListener {
            val bundle = viewModel.mapMeterGetToUpdateParcel(viewModel.meterParcelable).toBundle()
            findNavController().navigate(R.id.action_meterGetView_to_meterUpdateView, bundle)
        }
        if (viewModel.meterParcelable.currentReading == 0.0) {
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
        readingListAdapter = AdaptiveIntListAdapter(
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
