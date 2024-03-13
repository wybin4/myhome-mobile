package com.example.myhome.presentation.features.appeal.add.verify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.appeal.models.AppealUpdateMeterModel
import com.example.myhome.features.appeal.usecases.AppealAddUseCase
import com.example.myhome.features.common.usecases.SubscriberListUseCase
import com.example.myhome.features.meter.usecases.MeterListUseCase
import com.example.myhome.presentation.features.meter.MeterExtendedUiModel
import com.example.myhome.presentation.features.meter.MeterUiMapper
import com.example.myhome.presentation.models.asListResource
import com.example.myhome.presentation.models.asNetworkResult
import com.example.myhome.presentation.utils.mappers.ImageMapper
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
    private val _meterList = MutableLiveData<List<MeterExtendedUiModel>>()
    val meterList: LiveData<List<MeterExtendedUiModel>> = _meterList

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
            .collect {
                it.asListResource(mutableDataState) { data ->
                    _meterList.value = meterUiMapper.meterListToUi(data)
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
                        manageAddState(result)
                    }
            }
        } else {
            codeError()
        }
    }
}