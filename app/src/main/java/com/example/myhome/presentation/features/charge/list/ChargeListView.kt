package com.example.myhome.presentation.features.charge.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ConcatAdapter
import com.example.myhome.R
import com.example.myhome.databinding.ChargeListItemBinding
import com.example.myhome.databinding.ChargeListItemLoadingBinding
import com.example.myhome.databinding.ChargeListViewBinding
import com.example.myhome.databinding.ChargeListViewHeaderBinding
import com.example.myhome.databinding.DebtListItemBinding
import com.example.myhome.presentation.ConstantsUi.Companion.VERTICAL_LOADING_RECYCLER_VIEW_SIZE
import com.example.myhome.presentation.features.charge.ChargeColorPicker
import com.example.myhome.presentation.features.charge.adapters.ChargeListAdapter
import com.example.myhome.presentation.features.charge.adapters.ChargeListHeaderAdapter
import com.example.myhome.presentation.features.charge.chart.StackedChartManager
import com.example.myhome.presentation.features.charge.models.ChargeChartModel
import com.example.myhome.presentation.features.charge.models.ChargeUiModel
import com.example.myhome.presentation.features.charge.models.DebtUiModel
import com.example.myhome.presentation.features.charge.state.chargeHandleLoadState
import com.example.myhome.presentation.features.charge.state.list.ChargeListState
import com.example.myhome.presentation.features.charge.state.list.ChargeListStateManager
import com.example.myhome.presentation.utils.adapters.CustomPagingAdapter
import com.example.myhome.presentation.utils.adapters.InfiniteListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ChargeListView : Fragment() {
    private var _binding: ChargeListViewBinding? = null
    private val binding get() = _binding!!
    
    private var _headerBinding: ChargeListViewHeaderBinding? = null
    private val headerBinding get() = _headerBinding!!
    
    private val viewModel by viewModels<ChargeListViewModel>()

    private lateinit var chargeListAdapter: CustomPagingAdapter<ChargeUiModel, ChargeListItemBinding>
    private lateinit var chargeInfiniteListAdapter: InfiniteListAdapter<String, ChargeListItemLoadingBinding>

    private val listStateManager = ChargeListStateManager(this::updateViewState)
    private lateinit var chargeColorPicker: ChargeColorPicker

    private lateinit var headerAdapter: ChargeListHeaderAdapter
    private lateinit var debtListAdapter: ChargeListAdapter<DebtUiModel, DebtListItemBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ChargeListViewBinding.inflate(inflater, container, false)
        _headerBinding = ChargeListViewHeaderBinding.inflate(inflater, container, false)
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
        viewModel.fetchDebtAndChartLists()
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
        chargeListAdapter.addLoadStateListener {
            it.chargeHandleLoadState(viewModel::setState, chargeListAdapter.itemCount < 1)
        }
    }

    private fun observeLists() {
        viewModel.chargeList.observe(viewLifecycleOwner) {
            lifecycleScope.launch {
                chargeListAdapter.submitData(it)
            }
        }
        viewModel.debtList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                headerBinding.card.visibility = View.VISIBLE

                debtListAdapter.updateList(it)
                val totalDebtValue = it.sumOf { debt -> debt.outstandingDebt }
                headerBinding.totalDebt.text = viewModel.formatDouble2F(totalDebtValue)
            } else {
                headerBinding.card.visibility = View.GONE
            }
        }
        viewModel.chargeChartList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                headerBinding.barChart.visibility = View.VISIBLE
                setupBarChart(it)
            } else {
                headerBinding.barChart.visibility = View.GONE
            }
        }
    }

    private fun setupBarChart(chartData: List<ChargeChartModel>) {
        if (chartData.isNotEmpty()) {
            StackedChartManager(requireActivity(), headerBinding.barChart, chartData)
        }
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
        chargeListAdapter = CustomPagingAdapter(
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
        debtListAdapter = ChargeListAdapter(
            requireActivity(),
            itemBindingInflater = { inflater, parent, attachToParent ->
                DebtListItemBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item, _, _ ->
                binding.debtText.text = item.getText()
            },
            headerBinding.debtListLayout
        )

        headerAdapter = ChargeListHeaderAdapter(headerBinding)
        val concatAdapter = ConcatAdapter(headerAdapter, chargeListAdapter)
        binding.chargeRecyclerView.adapter = concatAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
