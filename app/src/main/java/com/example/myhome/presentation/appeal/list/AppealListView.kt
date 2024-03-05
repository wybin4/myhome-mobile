package com.example.myhome.presentation.appeal.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.AppealGetViewBinding
import com.example.myhome.databinding.AppealListItemBinding
import com.example.myhome.databinding.AppealListItemLoadingBinding
import com.example.myhome.databinding.AppealListViewBinding
import com.example.myhome.utils.ConstantsUi
import com.example.myhome.utils.adapters.CustomListAdapter
import com.example.myhome.utils.adapters.InfiniteListAdapter
import com.example.myhome.utils.managers.state.ListStateManager
import com.example.myhome.utils.models.AppealUiModel
import com.example.myhome.utils.models.ListState
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppealListView : Fragment() {
    private var _bindingList: AppealListViewBinding? = null
    private val bindingList get() = _bindingList!!
    private var _bindingGet: AppealGetViewBinding? = null
    private val bindingGet get() = _bindingGet!!

    private val viewModel by viewModels<AppealListViewModel>()

    private lateinit var appealListAdapter: CustomListAdapter<AppealUiModel, AppealListItemBinding>
    private lateinit var appealInfiniteListAdapter: InfiniteListAdapter<String, AppealListItemLoadingBinding>

    private lateinit var bottomSheetDialog: BottomSheetDialog

    private val listStateManager = ListStateManager(this::updateViewState)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingList = AppealListViewBinding.inflate(inflater, container, false)
        bindingList.lifecycleOwner = this

        setupRecyclerView()
        setupInfiniteRecyclerView()
        setupGetSheet(inflater, container)

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
        viewModel.appealList.observe(viewLifecycleOwner) { appealListAdapter.submitList(it) }
    }

    private fun observeResourceState() {
        viewModel.appealListState.observe(viewLifecycleOwner) { resource ->
            listStateManager.observeStates(resource)
        }
    }

    private fun setupRecyclerView() {
        appealListAdapter = CustomListAdapter(
            itemBindingInflater = { inflater, parent, attachToParent ->
                AppealListItemBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { bindingList, item ->
                bindingList.appeal = item
            },
            onItemClick = { item ->
                bindingGet.appeal = item
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
