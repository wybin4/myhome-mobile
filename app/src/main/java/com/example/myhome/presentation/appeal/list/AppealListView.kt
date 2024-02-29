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
import com.example.myhome.databinding.AppealListViewBinding
import com.example.myhome.utils.adapters.CustomListAdapter
import com.example.myhome.utils.models.AppealUiModel
import com.example.myhome.utils.models.Resource
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

    private lateinit var bottomSheetDialog: BottomSheetDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _bindingList = AppealListViewBinding.inflate(inflater, container, false)
        bindingList.lifecycleOwner = this

        setupRecyclerView()

        bindingList.addAppealButton.setOnClickListener {
            findNavController().navigate(R.id.action_appealListView_to_appealPickView)
        }

        bottomSheetDialog = BottomSheetDialog(requireActivity())
        _bindingGet = AppealGetViewBinding.inflate(inflater, container, false)
        bottomSheetDialog.setContentView(bindingGet.root)

        bindingList.bottomSheetDragHandle.setOnClickListener {
            if (bottomSheetDialog.isShowing) {
                bottomSheetDialog.dismiss()
            } else {
                bottomSheetDialog.show()
            }
        }

        return bindingList.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeList()
        observeResourceStates()
    }

    override fun onResume() {
        super.onResume()

        viewModel.fetchAppealList()
    }

    private fun observeList() {
        viewModel.appealList.observe(viewLifecycleOwner) { appealListAdapter.submitList(it) }
    }


    private fun observeResourceStates() {
        viewModel.appealListState.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> showLoadingState()
                is Resource.Success -> showSuccessState()
                is Resource.Empty -> showEmptyState()
                is Resource.Error -> showErrorState()
                else -> {}
            }
        }
    }

    private fun showLoadingState() {
//        bindingList.onLoading.visibility = View.VISIBLE
        bindingList.onSuccess.visibility = View.GONE
    }

    private fun showSuccessState() {
        bindingList.onSuccess.visibility = View.VISIBLE
//        bindingList.onLoading.visibility = View.GONE
    }

    private fun showEmptyState() {
        TODO("Доделать")
    }

    private fun showErrorState() {
        TODO("Доделать")
    }

    private fun setupRecyclerView() {
        appealListAdapter = CustomListAdapter(
            context = requireActivity(),
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
