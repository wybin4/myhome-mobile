package com.example.myhome.presentation.features.servicenotification.list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.myhome.databinding.ServiceNotificationListItemBinding
import com.example.myhome.databinding.ServiceNotificationListItemLoadingBinding
import com.example.myhome.databinding.ServiceNotificationListViewBinding
import com.example.myhome.presentation.ConstantsUi
import com.example.myhome.presentation.features.servicenotification.models.ServiceNotificationUiModel
import com.example.myhome.presentation.state.list.ListState
import com.example.myhome.presentation.state.list.ListStateManager
import com.example.myhome.presentation.utils.adapters.CustomListAdapter
import com.example.myhome.presentation.utils.adapters.InfiniteListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceNotificationListView : Fragment() {
    private var _binding: ServiceNotificationListViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ServiceNotificationListViewModel>()

    private lateinit var notificationListAdapter: CustomListAdapter<ServiceNotificationUiModel, ServiceNotificationListItemBinding>
    private lateinit var notificationInfiniteListAdapter: InfiniteListAdapter<String, ServiceNotificationListItemLoadingBinding>

    private val listStateManager = ListStateManager(this::updateViewState)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ServiceNotificationListViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupRecyclerViews()
        setupInfiniteRecyclerView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeResourceState()
        observeLists()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchNotificationList()
    }

    private fun updateViewState(state: ListState) {
        binding.apply {
            notificationInfiniteRecyclerView.visibility = state.loadingVisibility
            notificationRecyclerView.visibility = state.successVisibility
            onEmpty.visibility = state.emptyVisibility
            onError.visibility = state.errorVisibility
            state.errorMessage?.let { errorLayout.error = it }
        }
    }

    private fun observeResourceState() {
        viewModel.listState.observe(viewLifecycleOwner) { resource ->
            listStateManager.observeStates(resource)
        }
    }

    private fun observeLists() {
        viewModel.notificationList.observe(viewLifecycleOwner) { notificationListAdapter.submitList(it) }
    }

    private fun setupInfiniteRecyclerView() {
        notificationInfiniteListAdapter = InfiniteListAdapter(
            itemList = List(ConstantsUi.VERTICAL_LOADING_RECYCLER_VIEW_SIZE) { "" },
            itemBindingInflater = { inflater, parent, attachToParent ->
                ServiceNotificationListItemLoadingBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.item = item
            }
        )
        binding.notificationInfiniteRecyclerView.adapter = notificationInfiniteListAdapter
        binding.notificationInfiniteRecyclerView.hasFixedSize()
    }

    private fun setupRecyclerViews() {
        notificationListAdapter = CustomListAdapter(
            itemBindingInflater = { inflater, parent, attachToParent ->
                ServiceNotificationListItemBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.notification = item
            },
            onItemClick = { item ->
//                val bundle = viewModel.notificationUiMapper.notificationUiToGetParcel(item).toBundle()
//                findNavController().navigate(R.id.action_NotificationListView_to_NotificationGetView, bundle)
            }
        )
        binding.notificationRecyclerView.adapter = notificationListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}