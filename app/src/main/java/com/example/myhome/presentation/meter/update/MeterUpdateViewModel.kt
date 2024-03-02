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
import com.example.myhome.utils.mappers.ImageMapper
import com.example.myhome.utils.models.AddResource
import com.example.myhome.utils.models.MeterGetToUpdateParcelableModel
import com.example.myhome.utils.models.NetworkResult
import com.example.myhome.utils.models.Resource
import com.example.myhome.utils.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
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

    private val _dataState = MutableLiveData<Resource>(Resource.Loading)
    val dataState: LiveData<Resource> = _dataState

    private val _dataAddState = MutableLiveData<AddResource>(AddResource.None)
    val dataAddState: LiveData<AddResource> = _dataAddState

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
                .asNetworkResult()
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            val data = result.data
                            if (data.isNotEmpty()) {
                                _dataState.value = Resource.Success
                                _subscriberList.value = data
                            } else {
                                _dataState.value = Resource.Empty
                            }
                        }
                        is NetworkResult.Loading -> {
                            _dataState.value = Resource.Loading
                        }
                        is NetworkResult.Error -> {
                            val errorMessage = result.exception.message
                            if (errorMessage != null) {
                                _dataState.value = Resource.Error(errorMessage)
                            }
                        }
                    }
                }

            apartmentListUseCase()
                .asNetworkResult()
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            val data = result.data
                            if (data.isNotEmpty()) {
                                _dataState.value = Resource.Success
                                _apartmentList.value = data
                            } else {
                                _dataState.value = Resource.Empty
                            }
                        }
                        is NetworkResult.Loading -> {
                            _dataState.value = Resource.Loading
                        }
                        is NetworkResult.Error -> {
                            val errorMessage = result.exception.message
                            if (errorMessage != null) {
                                _dataState.value = Resource.Error(errorMessage)
                            }
                        }
                    }
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
                    .asNetworkResult()
                    .collect { result ->
                        when (result) {
                            is NetworkResult.Success -> {
                                val data = result.data
                                if (data) {
                                    _dataAddState.value = AddResource.Success
                                } else {
                                    _dataAddState.value = AddResource.CodeError
                                }
                            }
                            is NetworkResult.Loading -> {
                                _dataAddState.value = AddResource.Loading
                            }
                            is NetworkResult.Error -> {
                                val errorMessage = result.exception.message
                                if (errorMessage != null) {
                                    _dataAddState.value = AddResource.NetworkError(errorMessage)
                                }
                            }
                        }
                    }
            }
        } else {
            _dataAddState.value = AddResource.CodeError
        }
    }
}
