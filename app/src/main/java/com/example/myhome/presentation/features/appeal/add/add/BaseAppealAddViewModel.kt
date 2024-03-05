package com.example.myhome.presentation.features.appeal.add.add

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.appeal.models.AppealAddMeterModel
import com.example.myhome.features.appeal.models.AppealType
import com.example.myhome.features.appeal.usecases.AppealAddUseCase
import com.example.myhome.features.common.usecases.ApartmentListUseCase
import com.example.myhome.features.common.usecases.SubscriberListUseCase
import com.example.myhome.features.common.usecases.TypeOfServiceListUseCase
import com.example.myhome.presentation.features.appeal.add.BaseAppealViewModel
import com.example.myhome.presentation.features.common.CommonUiMapper
import com.example.myhome.presentation.features.common.models.ApartmentExtendedUiModel
import com.example.myhome.presentation.features.common.models.SubscriberUiModel
import com.example.myhome.presentation.features.common.models.TypeOfServiceUiModel
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.models.asListResource
import com.example.myhome.presentation.models.asNetworkResult
import com.example.myhome.presentation.utils.mappers.ImageMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BaseAppealAddViewModel @Inject constructor(
    val apartmentListUseCase: ApartmentListUseCase,
    val subscriberListUseCase: SubscriberListUseCase,
    val typeOfServiceListUseCase: TypeOfServiceListUseCase,
    private val appealAddUseCase: AppealAddUseCase,
    private val commonUiMapper: CommonUiMapper,
    private val imageMapper: ImageMapper
) : BaseAppealViewModel(AppealType.AddIndividualMeter) {
    private val _apartmentList = MutableLiveData<List<ApartmentExtendedUiModel>>()
    val apartmentList: LiveData<List<ApartmentExtendedUiModel>> = _apartmentList

    private val _typeOfServiceList = MutableLiveData<List<TypeOfServiceUiModel>>()
    val typeOfServiceList: LiveData<List<TypeOfServiceUiModel>> = _typeOfServiceList

    private val _subscriberList = MutableLiveData<List<SubscriberUiModel>>()
    private val subscriberList: LiveData<List<SubscriberUiModel>> = _subscriberList

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
                .collect {
                    it.asListResource(_dataState) { data ->
                        _apartmentList.value = commonUiMapper.mapApartmentListToUi(data)
                    }
                }

            typeOfServiceListUseCase()
                .asNetworkResult()
                .collect {
                    it.asListResource(_dataState) { data ->
                        _typeOfServiceList.value = commonUiMapper.typeOfServiceListToUi(data)
                    }
                }

            subscriberListUseCase()
                .asNetworkResult()
                .collect {
                    it.asListResource(_dataState) { data ->
                        _subscriberList.value = commonUiMapper.subscriberListToUi(data)
                    }
                }
        }
    }

    fun addMeter() {
        val selectedSubscriberId = apartmentList.value?.find { it.id == selectedApartmentId }?.subscriberId
        val selectedManagementCompanyId = subscriberList.value?.find { it.id == selectedSubscriberId }?.managementCompanyId
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
                        manageAddState(result)
                    }
            }
        } else {
            codeError()
        }
    }
}
