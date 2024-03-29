package com.example.myhome.presentation.features.charge.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.myhome.features.charge.repositories.ChargeRepository
import com.example.myhome.presentation.features.charge.ChargeUiMapper
import com.example.myhome.presentation.features.charge.converters.MoneyConverter
import com.example.myhome.presentation.features.charge.models.ChargeChartModel
import com.example.myhome.presentation.features.charge.models.ChargeUiModel
import com.example.myhome.presentation.features.charge.models.DebtUiModel
import com.example.myhome.presentation.features.charge.models.networkresults.asChargePagingDataResource
import com.example.myhome.presentation.features.charge.models.resources.ChargeListResource
import com.example.myhome.presentation.models.NetworkResult
import com.example.myhome.presentation.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChargeListViewModel @Inject constructor(
    private val chargeRepository: ChargeRepository,
    val chargeUiMapper: ChargeUiMapper
) : ViewModel(), MoneyConverter {
    private val _chargeList = MutableLiveData<PagingData<ChargeUiModel>>()
    val chargeList: LiveData<PagingData<ChargeUiModel>> = _chargeList

    private val _chargeChartList = MutableLiveData<List<ChargeChartModel>>()
    val chargeChartList: LiveData<List<ChargeChartModel>> = _chargeChartList

    private val _debtList = MutableLiveData<List<DebtUiModel>>()
    val debtList: LiveData<List<DebtUiModel>> = _debtList

    private val _listState = MutableLiveData<ChargeListResource>(ChargeListResource.Loading)
    val listState: LiveData<ChargeListResource> = _listState

    fun fetchChargeList() {
        viewModelScope.launch {
            chargeRepository.listCharge()
                .cachedIn(viewModelScope)
                .asNetworkResult()
                .collect {
                    it.asChargePagingDataResource(_listState) { data ->
                        _chargeList.value = data.map { d -> chargeUiMapper.chargeToUi(d) }
                    }
                }
        }
    }
    
    fun fetchDebtAndChartLists() {
        viewModelScope.launch {
            chargeRepository.listDebt()
                .asNetworkResult()
                .collect { resource ->
                    when (resource) {
                        is NetworkResult.Success -> {
                            val data = resource.data
                            if (data.isNotEmpty()) {
                                _debtList.value = chargeUiMapper.chargeListToDebtUi(data)
                            }
                        }
                        else -> {}
                    }
                }

            chargeRepository.listChargeChart()
                .asNetworkResult()
                .collect { resource ->
                    when (resource) {
                        is NetworkResult.Success -> {
                            _chargeChartList.value = chargeUiMapper.chargeListToChart(resource.data)
                        }
                        else -> {}
                    }
                }
        }
    }

    fun setState(resource: ChargeListResource) {
        _listState.value = resource
    }

}
