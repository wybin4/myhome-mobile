package com.example.myhome.presentation.features.charge.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.charge.repositories.ChargeRepository
import com.example.myhome.presentation.features.charge.ChargeUiMapper
import com.example.myhome.presentation.features.charge.converters.MoneyConverter
import com.example.myhome.presentation.features.charge.models.ChargeChartModel
import com.example.myhome.presentation.features.charge.models.ChargeUiModel
import com.example.myhome.presentation.features.charge.models.SpdDebtRelationTextListItem
import com.example.myhome.presentation.features.charge.models.networkresults.asChargeListResource
import com.example.myhome.presentation.features.charge.models.resources.ChargeListResource
import com.example.myhome.presentation.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChargeListViewModel @Inject constructor(
    private val chargeRepository: ChargeRepository,
    val chargeUiMapper: ChargeUiMapper
) : ViewModel(), MoneyConverter {
    private val _chargeList = MutableLiveData<List<ChargeUiModel>>()
    val chargeList: LiveData<List<ChargeUiModel>> = _chargeList

    private val _debtList = MutableLiveData<List<SpdDebtRelationTextListItem>>()
    val debtList: LiveData<List<SpdDebtRelationTextListItem>> = _debtList

    private val _listState = MutableLiveData<ChargeListResource>(ChargeListResource.Loading)
    val listState: LiveData<ChargeListResource> = _listState

    fun fetchChargeList() {
        viewModelScope.launch {
            chargeRepository.listCharge()
                .asNetworkResult()
                .collect {
                    it.asChargeListResource(_listState) { data ->
                        try {
                            _chargeList.value = chargeUiMapper.chargeListToUi(data)
                            _debtList.value = chargeUiMapper.chargeListToDebtUi(data)
                        } catch (e: IllegalArgumentException) {
                            _listState.value = ChargeListResource.CodeError
                        }
                    }
                }
        }
    }

    fun getChartData(): List<ChargeChartModel> {
        return _chargeList.value?.let { chargeUiMapper.chargeListToChart(it) } ?: emptyList()
    }

}
