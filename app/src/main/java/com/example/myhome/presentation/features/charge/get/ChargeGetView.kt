package com.example.myhome.presentation.features.charge.get

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.myhome.R
import com.example.myhome.databinding.ChargeGetViewBinding
import com.example.myhome.databinding.PaymentListItemBinding
import com.example.myhome.presentation.features.charge.ChargeListAdapter
import com.example.myhome.presentation.features.charge.models.PaymentUiModel
import com.example.myhome.presentation.features.charge.models.resources.ChargeGetResource
import com.example.myhome.presentation.features.charge.models.resources.ChargeListResource
import com.example.myhome.presentation.features.charge.state.ChargeCombinedState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChargeGetView : Fragment() {
    private var _binding: ChargeGetViewBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ChargeGetViewModel>()
    private lateinit var paymentListAdapter: ChargeListAdapter<PaymentUiModel, PaymentListItemBinding>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ChargeGetViewBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.chargeParcelable = requireArguments().getParcelable("charge")!!

        setupRecyclerView()
        setupActionButtons()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeResourceState()
        observeList()
    }

    override fun onResume() {
        super.onResume()
        viewModel.fetchData()
    }

    private fun setupActionButtons() {
        binding.getSpdButton.setOnClickListener {
            Log.e("getSpdButton", "clicked")
//            val bundle = viewModel.mapMeterGetToUpdateParcel(viewModel.meterParcelable).toBundle()
//            findNavController().navigate(R.id.action_meterGetView_to_meterUpdateView, bundle)
        }
        if (viewModel.chargeParcelable.outstandingDebt > 0) {
            binding.payChargeButton.setOnClickListener {
                val bundle = viewModel.mapChargeGetToPayParcel().toBundle()
                findNavController().navigate(R.id.action_chargeGetView_to_chargePayView, bundle)
            }
        } else {
            binding.payChargeButton.visibility = View.GONE
        }
    }

    private fun observeResourceState() {
        viewModel.paymentState.observe(viewLifecycleOwner) { paymentState ->
            viewModel.spdState.observe(viewLifecycleOwner) { spdState ->
                val combinedState = combineStates(paymentState, spdState)
                updateViewState(combinedState)
            }
        }
    }

    private fun setupRecyclerView() {
        paymentListAdapter = ChargeListAdapter(
            requireActivity(),
            itemBindingInflater = { inflater, parent, attachToParent ->
                PaymentListItemBinding.inflate(inflater, parent, attachToParent)
            },
            setBinding = { binding, item, position, count ->
                binding.payment = item
                if (position == count - 1) {
                    binding.line.visibility = View.GONE
                } else {
                    binding.line.visibility = View.VISIBLE
                }
            },
            binding.paymentsLayout
        )
    }

    private fun observeList() {
        viewModel.paymentList.observe(viewLifecycleOwner) {
            paymentListAdapter.updateList(it)
        }
    }

    private fun combineStates(paymentState: ChargeListResource, spdState: ChargeGetResource): ChargeCombinedState {
        return when {
            paymentState is ChargeListResource.Loading || spdState is ChargeGetResource.Loading -> ChargeCombinedState.Loading
            spdState is ChargeGetResource.NetworkError -> ChargeCombinedState.NetworkError(spdState.message)
            paymentState is ChargeListResource.NetworkError -> ChargeCombinedState.NetworkError(paymentState.message)
            spdState is ChargeGetResource.CodeError && paymentState is ChargeListResource.Success -> ChargeCombinedState.CodeError
            spdState is ChargeGetResource.Success -> ChargeCombinedState.Success(
                isSecondCodeError = paymentState is ChargeListResource.CodeError,
                isSecondEmpty = paymentState is ChargeListResource.Empty
            )
            else -> ChargeCombinedState.CodeError
        }
    }

    private fun updateViewState(state: ChargeCombinedState) {
        binding.apply {
            onLoading.visibility = state.loadingVisibility
            onLoadingButtons.visibility = state.loadingVisibility
            onSuccess.visibility = state.successVisibility
            onSuccessButtons.visibility = state.successVisibility
            onSuccessPaymentHistory.visibility = state.secondSuccessVisibility
            onEmptyPaymentHistory.visibility = state.secondEmptyVisibility
            onCodeErrorPaymentHistory.visibility = state.secondCodeErrorVisibility
            onCodeError.visibility = state.codeErrorVisibility
            onNetworkError.visibility = state.networkErrorVisibility
            state.errorMessage?.let { networkErrorLayout.error = it }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
