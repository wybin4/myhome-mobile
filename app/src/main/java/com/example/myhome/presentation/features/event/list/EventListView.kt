package com.example.myhome.presentation.features.event.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myhome.R
import com.example.myhome.databinding.EventFilterViewBinding
import com.example.myhome.databinding.EventListViewBinding
import com.example.myhome.presentation.features.event.adapters.EventListAdapter
import com.example.myhome.presentation.features.event.adapters.InfiniteEventListAdapter
import com.example.myhome.presentation.features.event.managers.EventFilterManager
import com.example.myhome.presentation.utils.filters.ListStateWithFilter
import com.example.myhome.presentation.utils.handleLoadStateWithFilter
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EventListView : Fragment() {
    private var _bindingList: EventListViewBinding? = null
    private val bindingList get() = _bindingList!!
    private var _bindingFilter: EventFilterViewBinding? = null
    private val bindingFilter get() = _bindingFilter!!

    private val viewModel by viewModels<EventListViewModel>()

    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var eventInfiniteListAdapter: InfiniteEventListAdapter

    private lateinit var filterDialog: BottomSheetDialog
    private lateinit var filterManager: EventFilterManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingList = EventListViewBinding.inflate(inflater, container, false)
        bindingList.lifecycleOwner = this

        setupRecyclerView()
        setupInfiniteRecyclerView()
        setupFilter(inflater, container)

        bindingList.filterButton.setOnClickListener {
            filterDialog.show()
        }
        bindingList.emptyFilterButton.setOnClickListener {
            filterDialog.show()
        }

        return bindingList.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeList()
        observeResourceState()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchEventList()
    }

    private fun updateViewState(state: ListStateWithFilter) {
        bindingList.apply {
            onLoading.visibility = state.loadingVisibility
            onSuccess.visibility = state.successVisibility
            onEmpty.visibility = state.emptyVisibility
            emptyFilterButton.visibility = state.emptyFilterVisibility
            onEmptyFilter.visibility = state.emptyFilterVisibility
            onError.visibility = state.errorVisibility
            state.errorMessage?.let { errorLayout.error = it }
        }
    }

    private fun setupInfiniteRecyclerView() {
        eventInfiniteListAdapter = InfiniteEventListAdapter(
            itemList = listOf("voting", "voting", "notification", "notification", "notification"),
        )
        bindingList.eventInfiniteRecyclerView.adapter = eventInfiniteListAdapter
        bindingList.eventInfiniteRecyclerView.hasFixedSize()
    }

    private fun observeList() {
        viewModel.eventList.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                eventListAdapter.submitData(it)
            }
        }
    }

    private fun observeResourceState() {
        viewModel.eventListState.observe(viewLifecycleOwner) { resource ->
            updateViewState(resource)
        }
        eventListAdapter.addLoadStateListener {
            it.handleLoadStateWithFilter(
                viewModel::setState,
                isItemCountNullable = eventListAdapter.itemCount < 1,
                isFilter = viewModel.observeManager.filters.isNotEmpty()
            )
        }
    }

    private fun setupFilter(inflater: LayoutInflater, container: ViewGroup?) {
        filterDialog = BottomSheetDialog(requireActivity(), R.style.SheetDialog)
        _bindingFilter = EventFilterViewBinding.inflate(inflater, container, false)
        filterDialog.setContentView(bindingFilter.root)

        bindingList.filterDragHandle.setOnClickListener {
            if (filterDialog.isShowing) {
                filterDialog.dismiss()
            } else {
                filterDialog.show()
            }
        }

        filterManager = EventFilterManager(
            requireActivity(), bindingFilter,
            viewModel.observeManager::setSecondList, viewModel.observeManager::getSecondList,
            viewModel.observeManager::setCreatedAt
        )
    }

    private fun setupRecyclerView() {
        eventListAdapter = EventListAdapter(requireActivity()) {
            viewModel.updateVoting(it.id)
        }
        bindingList.eventRecyclerView.adapter = eventListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingList = null
    }

}