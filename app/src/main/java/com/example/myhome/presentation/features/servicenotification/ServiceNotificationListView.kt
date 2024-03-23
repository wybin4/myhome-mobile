package com.example.myhome.presentation.features.servicenotification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.myhome.MainViewModel
import com.example.myhome.databinding.ServiceNotificationListItemBinding
import com.example.myhome.databinding.ServiceNotificationListItemLoadingBinding
import com.example.myhome.databinding.ServiceNotificationListViewBinding
import com.example.myhome.presentation.ConstantsUi
import com.example.myhome.presentation.features.servicenotification.models.ServiceNotificationUiModel
import com.example.myhome.presentation.utils.adapters.CustomListAdapter
import com.example.myhome.presentation.utils.adapters.InfiniteListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ServiceNotificationListView : Fragment() {
    private var _binding: ServiceNotificationListViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var notificationListAdapter: CustomListAdapter<ServiceNotificationUiModel, ServiceNotificationListItemBinding>
    private lateinit var notificationInfiniteListAdapter: InfiniteListAdapter<String, ServiceNotificationListItemLoadingBinding>

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

    private fun observeResourceState() {
        viewModel.notificationListState.observe(viewLifecycleOwner) { state ->
            binding.apply {
                notificationInfiniteRecyclerView.visibility = state.loadingVisibility
                notificationRecyclerView.visibility = state.successVisibility
                onEmpty.visibility = state.emptyVisibility
                onError.visibility = state.errorVisibility
                state.errorMessage?.let { errorLayout.error = it }
            }
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
            }, onItemClick = null
        )
        binding.notificationRecyclerView.adapter = notificationListAdapter
    }

    override fun onStop() {
        super.onStop()
        viewModel.readNotifications()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}