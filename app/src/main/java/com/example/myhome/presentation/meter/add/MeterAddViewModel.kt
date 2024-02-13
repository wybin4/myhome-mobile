package com.example.myhome.presentation.meter.add

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.appeal.models.AppealAddMeterModel
import com.example.myhome.appeal.usecases.AppealAddUseCase
import com.example.myhome.common.usecases.ApartmentListUseCase
import com.example.myhome.common.usecases.TypeOfServiceListUseCase
import com.example.myhome.common.models.ApartmentGetModel
import com.example.myhome.common.models.SubscriberGetModel
import com.example.myhome.common.models.TypeOfServiceGetModel
import com.example.myhome.common.usecases.SubscriberListUseCase
import com.example.myhome.presentation.ImageMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MeterAddViewModel @Inject constructor(
    private val apartmentListUseCase: ApartmentListUseCase,
    private val subscriberListUseCase: SubscriberListUseCase,
    private val typeOfServiceListUseCase: TypeOfServiceListUseCase,
    private val appealAddUseCase: AppealAddUseCase,
    private val imageMapper: ImageMapper
) : ViewModel() {
    private val _apartmentList = MutableLiveData<List<ApartmentGetModel>>()
    val apartmentList: LiveData<List<ApartmentGetModel>> = _apartmentList

    private val _typeOfServiceList = MutableLiveData<List<TypeOfServiceGetModel>>()
    val typeOfServiceList: LiveData<List<TypeOfServiceGetModel>> = _typeOfServiceList

    private val _subscriberList = MutableLiveData<List<SubscriberGetModel>>()
    private val subscriberList: LiveData<List<SubscriberGetModel>> = _subscriberList

    var selectedApartmentId: Int = -1
    var selectedTypeOfServiceId: Int = -1
    var selectedFactoryNumber: String = ""
    var selectIssuedAt: Date? = null
    var selectVerifiedAt: Date? = null
    var selectAttachment: Bitmap? = null

    init {
        fetchLists()
    }

    private fun fetchLists() {
        viewModelScope.launch {
            apartmentListUseCase()
                .flowOn(Dispatchers.IO)
                .catch {
                    _apartmentList.value = listOf()
                }
                .collect {
                    _apartmentList.value = it
                }

            typeOfServiceListUseCase()
                .flowOn(Dispatchers.IO)
                .catch {
                    _typeOfServiceList.value = listOf()
                }
                .collect {
                    _typeOfServiceList.value = it
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

    fun addMeter() {
        val selectedSubscriberId = apartmentList.value?.find { it.id == selectedApartmentId }?.subscriberId
        val selectedManagementCompanyId = subscriberList.value?.find { it.subscriberId == selectedSubscriberId }?.managementCompanyId
        val attachment = imageMapper.mapImageToDomain(selectAttachment!!)

        if (selectedManagementCompanyId !== null && selectedSubscriberId !== null) {
            viewModelScope.launch {
                appealAddUseCase.addMeter(
                    AppealAddMeterModel(
                        id = null,
                        apartmentId = selectedApartmentId,
                        typeOfServiceId = selectedTypeOfServiceId,
                        factoryNumber = selectedFactoryNumber,
                        verifiedAt = selectVerifiedAt!!,
                        issuedAt = selectIssuedAt!!,
                        attachment = attachment,
                        managementCompanyId = selectedManagementCompanyId,
                        subscriberId = selectedSubscriberId,
                    )
                )
            }
        } else {
            TODO("Ошибка?")
        }
    }
}
