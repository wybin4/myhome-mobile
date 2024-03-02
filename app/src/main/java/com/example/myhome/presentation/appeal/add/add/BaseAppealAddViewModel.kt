package com.example.myhome.presentation.appeal.add.add

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myhome.appeal.models.AppealAddMeterModel
import com.example.myhome.appeal.models.AppealType
import com.example.myhome.appeal.usecases.AppealAddUseCase
import com.example.myhome.common.models.ApartmentGetModel
import com.example.myhome.common.models.SubscriberGetModel
import com.example.myhome.common.models.TypeOfServiceGetModel
import com.example.myhome.common.usecases.ApartmentListUseCase
import com.example.myhome.common.usecases.SubscriberListUseCase
import com.example.myhome.common.usecases.TypeOfServiceListUseCase
import com.example.myhome.presentation.appeal.add.BaseAppealViewModel
import com.example.myhome.utils.mappers.ImageMapper
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
class BaseAppealAddViewModel @Inject constructor(
    val apartmentListUseCase: ApartmentListUseCase,
    val subscriberListUseCase: SubscriberListUseCase,
    val typeOfServiceListUseCase: TypeOfServiceListUseCase,
    private val appealAddUseCase: AppealAddUseCase,
    private val imageMapper: ImageMapper
) : BaseAppealViewModel(AppealType.AddIndividualMeter) {
    private val _apartmentList = MutableLiveData<List<ApartmentGetModel>>()
    val apartmentList: LiveData<List<ApartmentGetModel>> = _apartmentList

    private val _typeOfServiceList = MutableLiveData<List<TypeOfServiceGetModel>>()
    val typeOfServiceList: LiveData<List<TypeOfServiceGetModel>> = _typeOfServiceList

    private val _subscriberList = MutableLiveData<List<SubscriberGetModel>>()
    private val subscriberList: LiveData<List<SubscriberGetModel>> = _subscriberList

    private val _dataState = MutableLiveData<Resource>(Resource.Loading)
    val dataState: LiveData<Resource> = _dataState

    var selectIssuedAt: Date? = null
    var selectVerifiedAt: Date? = null
    var selectedApartmentId: Int = -1
    var selectedTypeOfServiceId: Int = -1
    var selectedFactoryNumber: String = ""
    var selectAttachment: Bitmap? = null

    init {
        fetchLists()
    }

    private fun fetchLists() {
        viewModelScope.launch {
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

            typeOfServiceListUseCase()
                .asNetworkResult()
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            val data = result.data
                            if (data.isNotEmpty()) {
                                _dataState.value = Resource.Success
                                _typeOfServiceList.value = data
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
