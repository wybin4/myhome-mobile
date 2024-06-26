package com.example.myhome.presentation.features.meter.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.ApartmentListItemBinding
import com.example.myhome.databinding.ApartmentListItemLoadingBinding
import com.example.myhome.databinding.MeterListItemBinding
import com.example.myhome.databinding.MeterListItemLoadingBinding
import com.example.myhome.databinding.MeterListViewBinding
import com.example.myhome.presentation.ConstantsUi.Companion.HORIZONTAL_LOADING_RECYCLER_VIEW_SIZE
import com.example.myhome.presentation.ConstantsUi.Companion.VERTICAL_LOADING_RECYCLER_VIEW_SIZE
import com.example.myhome.presentation.features.meter.ApartmentUiModel
import com.example.myhome.presentation.features.meter.MeterUiModel
import com.example.myhome.presentation.state.list.ListState
import com.example.myhome.presentation.utils.adapters.AdaptiveIntListAdapter
import com.example.myhome.presentation.utils.adapters.InfiniteListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MeterListView : Fragment() {
    private var _binding: MeterListViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MeterListViewModel>()
    
    private lateinit var meterListAdapter: AdaptiveIntListAdapter<MeterUiModel, MeterListItemBinding>
    private lateinit var apartmentListAdapter: AdaptiveIntListAdapter<ApartmentUiModel, ApartmentListItemBinding>
    
    private lateinit var meterInfiniteListAdapter: InfiniteListAdapter<String, MeterListItemLoadingBinding>
    private lateinit var apartmentInfiniteListAdapter: InfiniteListAdapter<String, ApartmentListItemLoadingBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = MeterListViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupRecyclerView()
        setupInfiniteRecyclerViews()
        setupCarouselView()

        binding.addMeterButton.setOnClickListener {
            addMeterClick()
        }
        binding.emptyAddMeterButton.setOnClickListener {
            addMeterClick()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeResourceState()
        observeLists()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchMeterList()
    }

    private fun addMeterClick() {
        findNavController().navigate(R.id.action_MeterListView_to_MeterAddView)
    }

    private fun updateViewState(state: ListState) {
        binding.apply {
            onLoading.visibility = state.loadingVisibility
            onSuccess.visibility = state.successVisibility
            onEmpty.visibility = state.emptyVisibility
            onError.visibility = state.errorVisibility
            emptyAddMeterButton.visibility = state.addButtonVisibility
            state.errorMessage?.let { errorLayout.error = it }
        }
    }

    private fun observeResourceState() {
        viewModel.meterListState.observe(viewLifecycleOwner) {
            updateViewState(it)
        }
    }

    private fun observeLists() {
        viewModel.meterList.observe(viewLifecycleOwner) { meterListAdapter.submitList(it) }
        viewModel.apartmentList.observe(viewLifecycleOwner) { apartmentListAdapter.submitList(it) }
    }

    private fun setupInfiniteRecyclerViews() {
        meterInfiniteListAdapter = InfiniteListAdapter(
            itemList = List(VERTICAL_LOADING_RECYCLER_VIEW_SIZE) { "" },
            itemBindingInflater = { inflater, parent, attachToParent ->
                MeterListItemLoadingBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.item = item
            }
        )
        binding.meterInfiniteRecyclerView.adapter = meterInfiniteListAdapter
        binding.meterInfiniteRecyclerView.hasFixedSize()

        apartmentInfiniteListAdapter = InfiniteListAdapter(
            itemList = List(HORIZONTAL_LOADING_RECYCLER_VIEW_SIZE) { "" },
            itemBindingInflater = { inflater, parent, attachToParent ->
                ApartmentListItemLoadingBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.item = item
            }
        )
        binding.apartmentInfiniteRecyclerView.adapter = apartmentInfiniteListAdapter
        binding.apartmentInfiniteRecyclerView.hasFixedSize()
    }

    private fun setupRecyclerView() {
        meterListAdapter = AdaptiveIntListAdapter(
            itemBindingInflater = { inflater, parent, attachToParent ->
                MeterListItemBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.meter = item
            },
            onItemClick = { item ->
                val bundle = viewModel.mapMeterUiToGetParcel(item).toBundle()
                findNavController().navigate(R.id.action_MeterListView_to_MeterGetView, bundle)
            }
        )

        binding.meterRecyclerView.adapter = meterListAdapter
    }

    private fun setupCarouselView() {
        apartmentListAdapter = AdaptiveIntListAdapter(
            itemBindingInflater = { inflater, parent, attachToParent ->
                ApartmentListItemBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.apartment = item
            },
            onItemClick = { item ->
                viewModel.changeSelectedApartment(item)
                apartmentListAdapter.notifyDataSetChanged()
            }
        )

        binding.apartmentRecyclerView.adapter = apartmentListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
