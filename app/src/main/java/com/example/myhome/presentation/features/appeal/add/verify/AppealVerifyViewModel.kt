package com.example.myhome.presentation.features.appeal.add.verify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.appeal.repositories.AppealRepository
import com.example.myhome.features.common.repositories.SubscriberRepository
import com.example.myhome.features.meter.repositories.MeterRepository
import com.example.myhome.presentation.features.appeal.AppealMapper
import com.example.myhome.presentation.features.appeal.AppealUpdateMeterUiModel
import com.example.myhome.presentation.features.common.CommonUiConverter
import com.example.myhome.presentation.features.meter.MeterExtendedUiModel
import com.example.myhome.presentation.features.meter.mappers.MeterMapper
import com.example.myhome.presentation.models.asListResource
import com.example.myhome.presentation.models.asNetworkResult
import com.example.myhome.presentation.utils.mappers.ImageMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppealVerifyViewModel @Inject constructor(
    private val meterRepository: MeterRepository,
    subscriberRepository: SubscriberRepository,
    private val appealRepository: AppealRepository,
    private val appealMapper: AppealMapper,
    private val commonUiConverter: CommonUiConverter,
    private val meterMapper: MeterMapper,
    private val imageMapper: ImageMapper
) : BaseAppealVerifyViewModel(subscriberRepository, commonUiConverter) {
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
        meterRepository.listMeter()
            .asNetworkResult()
            .collect {
                it.asListResource(mutableDataState) { data ->
                    _meterList.value = meterMapper.meterListToUi(data)
                }
            }

    }

    fun updateMeter() {
        val selectedSubscriberId = meterList.value?.find { it.id == selectedMeterId }?.subscriberId
        val selectedManagementCompanyId = subscriberList.value?.find { it.id == selectedSubscriberId }?.managementCompanyId
        val attachment = imageMapper.mapImageToDomain(selectAttachment!!)

        if (selectedManagementCompanyId !== null && selectedSubscriberId !== null) {
            viewModelScope.launch {
                appealRepository.updateMeter(
                    appealMapper.updateToRemote(
                        AppealUpdateMeterUiModel(
                            meterId = selectedMeterId,
                            verifiedAt = selectVerifiedAt!!,
                            issuedAt = selectIssuedAt!!,
                            managementCompanyId = selectedManagementCompanyId,
                            subscriberId = selectedSubscriberId,
                            attachment = attachment
                        )
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