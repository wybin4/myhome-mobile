package com.example.myhome.presentation.meter.update

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.appeal.models.AppealUpdateMeterModel
import com.example.myhome.appeal.usecases.AppealAddUseCase
import com.example.myhome.common.models.ApartmentGetModel
import com.example.myhome.common.models.SubscriberGetModel
import com.example.myhome.common.usecases.ApartmentListUseCase
import com.example.myhome.common.usecases.SubscriberListUseCase
import com.example.myhome.presentation.ImageMapper
import com.example.myhome.presentation.meter.models.MeterGetToUpdateParcelableModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MeterUpdateViewModel @Inject constructor(
    private val subscriberListUseCase: SubscriberListUseCase,
    private val apartmentListUseCase: ApartmentListUseCase,
    private val appealAddUseCase: AppealAddUseCase,
    private val imageMapper: ImageMapper
) : ViewModel() {
    private val _subscriberList = MutableLiveData<List<SubscriberGetModel>>()
    private val subscriberList: LiveData<List<SubscriberGetModel>> = _subscriberList

    private val _apartmentList = MutableLiveData<List<ApartmentGetModel>>()
    private val apartmentList: LiveData<List<ApartmentGetModel>> = _apartmentList

    lateinit var meterParcelable : MeterGetToUpdateParcelableModel

    var selectIssuedAt: Date? = null
    var selectVerifiedAt: Date? = null
    var selectAttachment: Bitmap? = null

    init {
        fetchLists()
    }

    private fun fetchLists() {
        viewModelScope.launch {
            subscriberListUseCase()
                .flowOn(Dispatchers.IO)
                .catch {
                    _subscriberList.value = listOf()
                }
                .collect {
                    _subscriberList.value = it
                }

            apartmentListUseCase()
                .flowOn(Dispatchers.IO)
                .catch {
                    _apartmentList.value = listOf()
                }
                .collect {
                    _apartmentList.value = it
                }
        }
    }

    fun updateMeter() {
        val selectedSubscriberId = apartmentList.value?.find { it.id == meterParcelable.apartmentId }?.subscriberId
        val selectedManagementCompanyId = subscriberList.value?.find { it.subscriberId == selectedSubscriberId }?.managementCompanyId
        val attachment = imageMapper.mapImageToDomain(selectAttachment!!)

        if (selectedManagementCompanyId !== null && selectedSubscriberId !== null) {
            viewModelScope.launch {
                appealAddUseCase.updateMeter(
                    AppealUpdateMeterModel(
                        meterId = meterParcelable.meterId,
                        verifiedAt = selectVerifiedAt!!,
                        issuedAt = selectIssuedAt!!,
                        managementCompanyId = selectedManagementCompanyId,
                        subscriberId = selectedSubscriberId,
                        attachment = attachment
                    )
                )
            }
        } else {
            TODO("Ошибка?")
        }

    }
}
