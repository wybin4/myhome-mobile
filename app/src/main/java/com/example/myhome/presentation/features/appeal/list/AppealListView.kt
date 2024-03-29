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
import com.example.myhome.di.RetrofitModule.Companion.BASE_URL
import com.example.myhome.presentation.ConstantsUi
import com.example.myhome.presentation.features.appeal.AppealUiModel
import com.example.myhome.presentation.state.list.ListState
import com.example.myhome.presentation.state.list.ListStateManager
import com.example.myhome.presentation.utils.adapters.CustomPagingAdapter
import com.example.myhome.presentation.utils.adapters.InfiniteListAdapter
import com.example.myhome.presentation.utils.handleLoadState
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

    private val viewModel by viewModels<AppealListViewModel>()

    private lateinit var appealListAdapter: CustomPagingAdapter<AppealUiModel, AppealListItemBinding>
    private lateinit var appealInfiniteListAdapter: InfiniteListAdapter<String, AppealListItemLoadingBinding>

    private lateinit var bottomSheetDialog: BottomSheetDialog

    private val listStateManager = ListStateManager(this::updateViewState)

    private lateinit var storagePermissionPicker: StoragePermissionPicker

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingList = AppealListViewBinding.inflate(inflater, container, false)
        bindingList.lifecycleOwner = this

        setupRecyclerView()
        setupInfiniteRecyclerView()
        setupGetSheet(inflater, container)

        storagePermissionPicker = StoragePermissionPicker(requireActivity())

        bindingList.addAppealButton.setOnClickListener {
            findNavController().navigate(R.id.action_appealListView_to_appealPickView)
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

    private fun updateViewState(state: ListState) {
        bindingList.apply {
            onLoading.visibility = state.loadingVisibility
            onSuccess.visibility = state.successVisibility
            onEmpty.visibility = state.emptyVisibility
            onError.visibility = state.errorVisibility
            addButtonLayout.visibility = state.addButtonLayoutVisibility ?: View.GONE
            state.errorMessage?.let { errorLayout.error = it }
        }
    }

    private fun setupGetSheet(inflater: LayoutInflater, container: ViewGroup?) {
        bottomSheetDialog = BottomSheetDialog(requireActivity(), R.style.SheetDialog)
        _bindingGet = AppealGetViewBinding.inflate(inflater, container, false)
        bottomSheetDialog.setContentView(bindingGet.root)

        bindingList.bottomSheetDragHandle.setOnClickListener {
            if (bottomSheetDialog.isShowing) {
                bottomSheetDialog.dismiss()
            } else {
                bottomSheetDialog.show()
            }
        }
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
            listStateManager.observeStates(resource)
        }
        appealListAdapter.addLoadStateListener {
            it.handleLoadState(viewModel::setState, appealListAdapter.itemCount < 1)
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
                bottomSheetDialog.show()
            }
        )
        bindingList.appealRecyclerView.adapter = appealListAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _bindingList = null
    }

}
