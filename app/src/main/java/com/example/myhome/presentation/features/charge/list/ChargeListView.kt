package com.example.myhome.presentation.features.charge.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.ChargeListItemBinding
import com.example.myhome.databinding.ChargeListItemLoadingBinding
import com.example.myhome.databinding.ChargeListViewBinding
import com.example.myhome.databinding.DebtListItemBinding
import com.example.myhome.presentation.ConstantsUi.Companion.VERTICAL_LOADING_RECYCLER_VIEW_SIZE
import com.example.myhome.presentation.features.charge.ChargeColorPicker
import com.example.myhome.presentation.features.charge.ChargeListAdapter
import com.example.myhome.presentation.features.charge.models.ChargeUiModel
import com.example.myhome.presentation.features.charge.models.SpdDebtRelationTextListItem
import com.example.myhome.presentation.features.charge.state.list.ChargeListState
import com.example.myhome.presentation.features.charge.state.list.ChargeListStateManager
import com.example.myhome.presentation.utils.adapters.CustomListAdapter
import com.example.myhome.presentation.utils.adapters.InfiniteListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChargeListView : Fragment() {
    private var _binding: ChargeListViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ChargeListViewModel>()
    
    private lateinit var chargeListAdapter: CustomListAdapter<ChargeUiModel, ChargeListItemBinding>
    private lateinit var chargeInfiniteListAdapter: InfiniteListAdapter<String, ChargeListItemLoadingBinding>

    private val listStateManager = ChargeListStateManager(this::updateViewState)
    private lateinit var chargeColorPicker: ChargeColorPicker

    private lateinit var debtListAdapter: ChargeListAdapter<SpdDebtRelationTextListItem, DebtListItemBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChargeListViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        setupRecyclerViews()
        setupInfiniteRecyclerView()
        chargeColorPicker = ChargeColorPicker(requireActivity())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeResourceState()
        observeLists()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchChargeList()
    }

    private fun updateViewState(state: ChargeListState) {
        binding.apply {
            onLoading.visibility = state.loadingVisibility
            onSuccess.visibility = state.successVisibility
            onEmpty.visibility = state.emptyVisibility
            onNetworkError.visibility = state.networkErrorVisibility
            onCodeError.visibility = state.codeErrorVisibility
            state.errorMessage?.let { networkErrorLayout.error = it }
        }
    }

    private fun observeResourceState() {
        viewModel.listState.observe(viewLifecycleOwner) { resource ->
            listStateManager.observeStates(resource)
        }
    }

    private fun observeLists() {
        viewModel.chargeList.observe(viewLifecycleOwner) { chargeListAdapter.submitList(it) }
        viewModel.debtList.observe(viewLifecycleOwner) { debtListAdapter.updateList(it) }
    }

    private fun setupInfiniteRecyclerView() {
        chargeInfiniteListAdapter = InfiniteListAdapter(
            itemList = List(VERTICAL_LOADING_RECYCLER_VIEW_SIZE) { "" },
            itemBindingInflater = { inflater, parent, attachToParent ->
                ChargeListItemLoadingBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.item = item
            }
        )
        binding.chargeInfiniteRecyclerView.adapter = chargeInfiniteListAdapter
        binding.chargeInfiniteRecyclerView.hasFixedSize()
    }

    private fun setupRecyclerViews() {
        chargeListAdapter = CustomListAdapter(
            itemBindingInflater = { inflater, parent, attachToParent ->
                ChargeListItemBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item ->
                binding.charge = item
                binding.amountChangeText.setTextColor(chargeColorPicker.getAmountChangeColor(item))
            },
            onItemClick = { item ->
                val bundle = viewModel.chargeUiMapper.chargeUiToGetParcel(item).toBundle()
                findNavController().navigate(R.id.action_ChargeListView_to_ChargeGetView, bundle)
            }
        )

        binding.chargeRecyclerView.adapter = chargeListAdapter

        debtListAdapter = ChargeListAdapter(
            requireActivity(),
            itemBindingInflater = { inflater, parent, attachToParent ->
                DebtListItemBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item, _, _ ->
                binding.debtText.text = item.getText()
            },
            binding.debtListLayout
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
