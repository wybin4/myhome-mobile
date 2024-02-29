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

    private var selectIssuedAt: Date? = null
    private var selectVerifiedAt: Date? = null
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
