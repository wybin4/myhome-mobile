package com.example.myhome.presentation.features.servicenotification

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.myhome.databinding.ServiceNotificationListItemBinding
import com.example.myhome.databinding.ServiceNotificationListItemLoadingBinding
import com.example.myhome.databinding.ServiceNotificationListViewBinding
import com.example.myhome.features.CommonSocketService
import com.example.myhome.presentation.ConstantsUi
import com.example.myhome.presentation.features.servicenotification.models.ServiceNotificationUiModel
import com.example.myhome.presentation.state.list.ListState
import com.example.myhome.presentation.utils.adapters.CustomPagingAdapter
import com.example.myhome.presentation.utils.adapters.InfiniteListAdapter
import com.example.myhome.presentation.utils.handleLoadState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ServiceNotificationListView : Fragment() {
    private var _binding: ServiceNotificationListViewBinding? = null
    private val binding get() = _binding!!

    private var isServiceBound = false

    private val viewModel by viewModels<ServiceNotificationListViewModel>()

    private lateinit var notificationListAdapter: CustomPagingAdapter<ServiceNotificationUiModel, ServiceNotificationListItemBinding>
    private lateinit var notificationInfiniteListAdapter: InfiniteListAdapter<String, ServiceNotificationListItemLoadingBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ServiceNotificationListViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        val serviceIntent = Intent(requireContext(), CommonSocketService::class.java)
        requireContext().bindService(serviceIntent, viewModel.getServiceConnection(), Context.BIND_AUTO_CREATE)

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

    private fun observeResourceState() {
        viewModel.notificationListState.observe(viewLifecycleOwner) {
           updateViewState(it)
        }
        notificationListAdapter.addLoadStateListener {
            it.handleLoadState(
                viewModel::setState, notificationListAdapter.itemCount < 1
            )
        }
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

    private fun observeLists() {
        viewModel.notificationList.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                notificationListAdapter.submitData(it)
            }
        }
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
        notificationListAdapter = CustomPagingAdapter(
            itemBindingInflater = { inflater, parent, attachToParent ->
                ServiceNotificationListItemBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.notification = item
            }, onItemClick = null
        )
        binding.notificationRecyclerView.adapter = notificationListAdapter
        binding.notificationRecyclerView.itemAnimator = null
    }

    override fun onStop() {
        super.onStop()
        viewModel.readNotifications()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        if (isServiceBound) {
            requireContext().unbindService(viewModel.getServiceConnection())
            isServiceBound = false
        }
    }

}