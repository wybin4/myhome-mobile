package com.example.myhome.presentation.appeal.add.verify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myhome.appeal.models.AppealUpdateMeterModel
import com.example.myhome.appeal.usecases.AppealAddUseCase
import com.example.myhome.common.usecases.SubscriberListUseCase
import com.example.myhome.meter.usecases.MeterListUseCase
import com.example.myhome.utils.mappers.ImageMapper
import com.example.myhome.utils.mappers.MeterUiMapper
import com.example.myhome.utils.models.MeterListItemUiModel
import com.example.myhome.utils.models.NetworkResult
import com.example.myhome.utils.models.Resource
import com.example.myhome.utils.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppealVerifyViewModel @Inject constructor(
    private val meterListUseCase: MeterListUseCase,
    subscriberListUseCase: SubscriberListUseCase,
    private val meterUiMapper: MeterUiMapper,
    private val appealAddUseCase: AppealAddUseCase,
    private val imageMapper: ImageMapper
) : BaseAppealVerifyViewModel(subscriberListUseCase) {
    private val _meterList = MutableLiveData<List<MeterListItemUiModel>>()
    val meterList: LiveData<List<MeterListItemUiModel>> = _meterList

    var selectedMeterId = -1

    init {
        viewModelScope.launch {
            fetchMeterList()
            fetchSubscriberList()
        }
    }

    private suspend fun fetchMeterList() {
        meterListUseCase()
            .asNetworkResult()
            .collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val data = result.data
                        if (data.isNotEmpty()) {
                            setDataGetState(Resource.Success)
                            _meterList.value = meterUiMapper.mapMeterListToUi(data)
                        } else {
                            setDataGetState(Resource.Empty)
                        }
                    }
                    is NetworkResult.Loading -> {
                        setDataGetState(Resource.Loading)
                    }
                    is NetworkResult.Error -> {
                        val errorMessage = result.exception.message
                        if (errorMessage != null) {
                            setDataGetState(Resource.Error(errorMessage))
                        }
                    }
                }
            }
    }

    fun updateMeter() {
        val selectedSubscriberId = meterList.value?.find { it.id == selectedMeterId }?.subscriberId
        val selectedManagementCompanyId = subscriberList.value?.find { it.subscriberId == selectedSubscriberId }?.managementCompanyId
        val attachment = imageMapper.mapImageToDomain(selectAttachment!!)

        if (selectedManagementCompanyId !== null && selectedSubscriberId !== null) {
            viewModelScope.launch {
                appealAddUseCase.updateMeter(
                    AppealUpdateMeterModel(
                        meterId = selectedMeterId,
                        verifiedAt = selectVerifiedAt!!,
                        issuedAt = selectIssuedAt!!,
                        managementCompanyId = selectedManagementCompanyId,
                        subscriberId = selectedSubscriberId,
                        attachment = attachment
                    )
                )
                    .asNetworkResult()
                    .collect { result ->
                        manageState(result)
                    }
            }
        } else {
            codeError()
        }
    }
}