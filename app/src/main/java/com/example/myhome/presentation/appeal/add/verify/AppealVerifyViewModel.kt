package com.example.myhome.presentation.appeal.add.verify

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myhome.appeal.models.AppealType
import com.example.myhome.appeal.models.AppealUpdateMeterModel
import com.example.myhome.appeal.usecases.AppealAddUseCase
import com.example.myhome.common.models.SubscriberGetModel
import com.example.myhome.common.usecases.SubscriberListUseCase
import com.example.myhome.meter.usecases.MeterListUseCase
import com.example.myhome.presentation.appeal.add.BaseAppealViewModel
import com.example.myhome.utils.mappers.ImageMapper
import com.example.myhome.utils.mappers.MeterUiMapper
import com.example.myhome.utils.models.MeterListItemUiModel
import com.example.myhome.utils.models.NetworkResult
import com.example.myhome.utils.models.Resource
import com.example.myhome.utils.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class AppealVerifyViewModel @Inject constructor(
    private val meterListUseCase: MeterListUseCase,
    private val subscriberListUseCase: SubscriberListUseCase,
    private val meterUiMapper: MeterUiMapper,
    private val appealAddUseCase: AppealAddUseCase,
    private val imageMapper: ImageMapper
) : BaseAppealViewModel(AppealType.VerifyIndividualMeter) {
    private val _meterList = MutableLiveData<List<MeterListItemUiModel>>()
    val meterList: LiveData<List<MeterListItemUiModel>> = _meterList

    private val _subscriberList = MutableLiveData<List<SubscriberGetModel>>()
    private val subscriberList: LiveData<List<SubscriberGetModel>> = _subscriberList

    private var selectIssuedAt: Date? = null
    private var selectVerifiedAt: Date? = null
    var selectedMeterId = -1
    var selectAttachment: Bitmap? = null

    init {
        fetchLists()
    }

    private fun fetchLists() {
        viewModelScope.launch {
            meterListUseCase()
                .flowOn(Dispatchers.IO)
                .catch {
                    _meterList.value = listOf()
                }
                .collect {
                    _meterList.value = meterUiMapper.mapMeterListToUi(it)
                }

            subscriberListUseCase()
                .flowOn(Dispatchers.IO)
                .catch {
                    _subscriberList.value = listOf()
                }
                .collect {
                    _subscriberList.value = it
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
                        when (result) {
                            is NetworkResult.Success -> {
                                val data = result.data
                                if (data) {
                                    _appealState.value = Resource.Success
                                } else {
                                    _appealState.value = Resource.Error
                                }
                            }
                            is NetworkResult.Loading -> {
                                _appealState.value = Resource.Loading
                            }
                            is NetworkResult.Error -> {
                                _appealState.value = Resource.Error
                            }
                        }
                    }
            }
        } else {
            TODO("Ошибка?")
        }
    }
}