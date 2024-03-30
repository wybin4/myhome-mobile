package com.example.myhome.presentation.features.appeal.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.AppealGetViewBinding
import com.example.myhome.databinding.AppealListItemBinding
import com.example.myhome.databinding.AppealListItemLoadingBinding
import com.example.myhome.databinding.AppealListViewBinding
import com.example.myhome.databinding.AppealFilterViewBinding
import com.example.myhome.di.RetrofitModule.Companion.BASE_URL
import com.example.myhome.presentation.ConstantsUi
import com.example.myhome.presentation.features.appeal.AppealFilterManager
import com.example.myhome.presentation.features.appeal.AppealUiModel
import com.example.myhome.presentation.utils.adapters.CustomPagingAdapter
import com.example.myhome.presentation.utils.adapters.InfiniteListAdapter
import com.example.myhome.presentation.utils.filters.ListStateWithFilter
import com.example.myhome.presentation.utils.handleLoadStateWithFilter
import com.example.myhome.presentation.utils.pickers.permissions.StoragePermissionPicker
import com.example.myhome.presentation.utils.resources.AndroidDownloader
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AppealListView : Fragment() {
    private var _bindingList: AppealListViewBinding? = null
    private val bindingList get() = _bindingList!!
    private var _bindingGet: AppealGetViewBinding? = null
    private val bindingGet get() = _bindingGet!!
    private var _bindingFilter: AppealFilterViewBinding? = null
    private val bindingFilter get() = _bindingFilter!!

    private val viewModel by viewModels<AppealListViewModel>()

    private lateinit var appealListAdapter: CustomPagingAdapter<AppealUiModel, AppealListItemBinding>
    private lateinit var appealInfiniteListAdapter: InfiniteListAdapter<String, AppealListItemLoadingBinding>

    private lateinit var appealGetDialog: BottomSheetDialog
    private lateinit var filterDialog: BottomSheetDialog
    private lateinit var filterManager: AppealFilterManager

    private lateinit var storagePermissionPicker: StoragePermissionPicker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingList = AppealListViewBinding.inflate(inflater, container, false)
        bindingList.lifecycleOwner = this

        setupRecyclerView()
        setupInfiniteRecyclerView()
        setupDialog(inflater, container)
        setupFilter(inflater, container)

        storagePermissionPicker = StoragePermissionPicker(requireActivity())

        bindingList.addAppealButton.setOnClickListener {
            findNavController().navigate(R.id.action_appealListView_to_appealPickView)
        }
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
        viewModel.fetchAppealList()
    }

    private fun updateViewState(state: ListStateWithFilter) {
        bindingList.apply {
            onLoading.visibility = state.loadingVisibility
            onSuccess.visibility = state.successVisibility
            onEmpty.visibility = state.emptyVisibility
            onEmptyFilter.visibility = state.emptyFilterVisibility
            onError.visibility = state.errorVisibility
            addAppealButton.visibility = state.addButtonVisibility
            emptyFilterButton.visibility = state.emptyFilterVisibility
            state.errorMessage?.let { errorLayout.error = it }
        }
    }

    private fun setupDialog(inflater: LayoutInflater, container: ViewGroup?) {
        appealGetDialog = BottomSheetDialog(requireActivity(), R.style.SheetDialog)
        _bindingGet = AppealGetViewBinding.inflate(inflater, container, false)
        appealGetDialog.setContentView(bindingGet.root)

        bindingList.appealGetDragHandle.setOnClickListener {
            if (appealGetDialog.isShowing) {
                appealGetDialog.dismiss()
            } else {
                appealGetDialog.show()
            }
        }
    }

    private fun setupFilter(inflater: LayoutInflater, container: ViewGroup?) {
        filterDialog = BottomSheetDialog(requireActivity(), R.style.SheetDialog)
        _bindingFilter = AppealFilterViewBinding.inflate(inflater, container, false)
        filterDialog.setContentView(bindingFilter.root)

        bindingList.filterDragHandle.setOnClickListener {
            if (filterDialog.isShowing) {
                filterDialog.dismiss()
            } else {
                filterDialog.show()
            }
        }

        filterManager = AppealFilterManager(
            requireActivity(), bindingFilter,
            viewModel.observeManager::setSecondList, viewModel.observeManager::getSecondList,
            viewModel.observeManager::setCreatedAt
        )
    }

    private fun setupInfiniteRecyclerView() {
        appealInfiniteListAdapter = InfiniteListAdapter(
            itemList = List(ConstantsUi.VERTICAL_LOADING_RECYCLER_VIEW_SIZE) { "" },
            itemBindingInflater = { inflater, parent, attachToParent ->
                AppealListItemLoadingBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.item = item
            }
        )
        bindingList.appealInfiniteRecyclerView.adapter = appealInfiniteListAdapter
        bindingList.appealInfiniteRecyclerView.hasFixedSize()
    }

    private fun observeList() {
        viewModel.appealList.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                appealListAdapter.submitData(it)
            }
        }
    }

    private fun observeResourceState() {
        viewModel.appealListState.observe(viewLifecycleOwner) { resource ->
            updateViewState(resource)
        }
        appealListAdapter.addLoadStateListener {
            it.handleLoadStateWithFilter(
                viewModel::setState,
                isItemCountNullable = appealListAdapter.itemCount < 1,
                isFilter = viewModel.observeManager.filters.isNotEmpty()
            )
        }
    }

    private fun setupRecyclerView() {
        appealListAdapter = CustomPagingAdapter(
            itemBindingInflater = { inflater, parent, attachToParent ->
                AppealListItemBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { bindingList, item ->
                bindingList.appeal = item
            },
            onItemClick = { item ->
                bindingGet.appeal = item
                bindingGet.downloadAppeal.setOnClickListener {
                    if (item.attachment != null) {
                        storagePermissionPicker.checkStoragePermission {
                            val downloader = AndroidDownloader(requireActivity())
                            downloader.downloadImage(BASE_URL + "image/" + item.attachment, item.attachment)
                        }
                    }
                }
                appealGetDialog.show()
            }
        )
        bindingList.appealRecyclerView.adapter = appealListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingList = null
    }

}
