package com.example.myhome.presentation.features.charge.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.charge.repositories.ChargeRepository
import com.example.myhome.presentation.features.charge.ChargeUiMapper
import com.example.myhome.presentation.features.charge.converters.MoneyConverter
import com.example.myhome.presentation.features.charge.models.resources.ChargeListResource
import com.example.myhome.presentation.features.charge.models.ChargeUiModel
import com.example.myhome.presentation.features.charge.models.SpdDebtRelationTextListItem
import com.example.myhome.presentation.features.charge.models.networkresults.pairNetworkResultAsChargeListResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.myhome.presentation.features.charge.models.networkresults.pairAsNetworkResult

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
                .pairAsNetworkResult()
                .collect {
                    it.pairNetworkResultAsChargeListResource(_listState) { data ->
                        try {
                            _chargeList.value = chargeUiMapper.chargeListToUi(data.first, data.second)
                            _debtList.value = chargeUiMapper.spdDebtRelationTextListToUi(data.first, data.second)
                        } catch (e: IllegalArgumentException) {
                            _listState.value = ChargeListResource.CodeError
                        }
                    }
                }
        }
    }

}
