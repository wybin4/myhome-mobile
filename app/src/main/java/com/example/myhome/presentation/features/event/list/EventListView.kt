package com.example.myhome.presentation.features.event.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myhome.databinding.EventListViewBinding
import com.example.myhome.presentation.state.list.ListState
import com.example.myhome.presentation.state.list.ListStateManager
import com.example.myhome.presentation.features.event.adapters.EventListAdapter
import com.example.myhome.presentation.features.event.adapters.InfiniteEventListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EventListView : Fragment() {
    private var _binding: EventListViewBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<EventListViewModel>()

    private lateinit var eventListAdapter: EventListAdapter
    private lateinit var eventInfiniteListAdapter: InfiniteEventListAdapter

    private val listStateManager = ListStateManager(this::updateViewState)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = EventListViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupRecyclerView()
        setupInfiniteRecyclerView()

        return binding.root
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

    private fun updateViewState(state: ListState) {
        binding.apply {
            onLoading.visibility = state.loadingVisibility
            onSuccess.visibility = state.successVisibility
            onEmpty.visibility = state.emptyVisibility
            onError.visibility = state.errorVisibility
            state.errorMessage?.let { errorLayout.error = it }
        }
    }

    private fun setupInfiniteRecyclerView() {
        eventInfiniteListAdapter = InfiniteEventListAdapter(
            itemList = listOf("voting", "voting", "notification", "notification", "notification"),
        )
        binding.eventInfiniteRecyclerView.adapter = eventInfiniteListAdapter
        binding.eventInfiniteRecyclerView.hasFixedSize()
    }

    private fun observeList() {
        viewModel.eventList.observe(viewLifecycleOwner) { eventListAdapter.submitList(it) }
    }

    private fun observeResourceState() {
        viewModel.eventListState.observe(viewLifecycleOwner) { resource ->
            listStateManager.observeStates(resource)
        }
    }

    private fun setupRecyclerView() {
        eventListAdapter = EventListAdapter(requireActivity()) {
            viewModel.updateVoting(it.id)
        }
        binding.eventRecyclerView.adapter = eventListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}