package com.example.myhome.presentation.features.appeal.add.add

import android.graphics.Bitmap

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.appeal.models.AppealType
import com.example.myhome.features.appeal.repositories.AppealRepository
import com.example.myhome.features.common.repositories.ApartmentRepository
import com.example.myhome.features.common.repositories.SubscriberRepository
import com.example.myhome.features.common.repositories.TypeOfServiceRepository
import com.example.myhome.presentation.features.appeal.AppealAddMeterUiModel
import com.example.myhome.presentation.features.appeal.AppealMapper
import com.example.myhome.presentation.features.appeal.add.BaseAppealViewModel
import com.example.myhome.presentation.features.common.CommonUiConverter
import com.example.myhome.presentation.features.common.models.ApartmentExtendedUiModel
import com.example.myhome.presentation.features.common.models.SubscriberUiModel
import com.example.myhome.presentation.features.common.models.TypeOfServiceUiModel
import com.example.myhome.presentation.models.asGetState
import com.example.myhome.presentation.models.asNetworkResult
import com.example.myhome.presentation.state.get.GetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class BaseAppealAddViewModel @Inject constructor(
    private val apartmentRepository: ApartmentRepository,
    private val subscriberRepository: SubscriberRepository,
    private val typeOfServiceRepository: TypeOfServiceRepository,
    private val appealRepository: AppealRepository,
    private val appealMapper: AppealMapper,
    private val commonUiMapper: CommonUiConverter
) : BaseAppealViewModel(AppealType.AddIndividualMeter) {
    private val _apartmentList = MutableLiveData<List<ApartmentExtendedUiModel>>()
    val apartmentList: LiveData<List<ApartmentExtendedUiModel>> = _apartmentList

    private val _typeOfServiceList = MutableLiveData<List<TypeOfServiceUiModel>>()
    val typeOfServiceList: LiveData<List<TypeOfServiceUiModel>> = _typeOfServiceList

    private val _subscriberList = MutableLiveData<List<SubscriberUiModel>>()
    private val subscriberList: LiveData<List<SubscriberUiModel>> = _subscriberList

    private val _getState = MutableLiveData<GetState>(GetState.Loading)
    val getState: LiveData<GetState> = _getState

    var selectIssuedAt: Date? = null
    var selectVerifiedAt: Date? = null
    var selectedApartmentId: Int = -1
    var selectedTypeOfServiceId: Int = -1
    var selectedFactoryNumber: String = ""

    init {
        fetchLists()
    }

    private fun fetchLists() {
        viewModelScope.launch {
            apartmentRepository.listApartment()
                .asNetworkResult()
                .collect {
                    it.asGetState(_getState) { data ->
                        _apartmentList.value = commonUiMapper.apartmentListToUi(data)
                    }
                }

            typeOfServiceRepository.listTypeOfService()
                .asNetworkResult()
                .collect {
                    it.asGetState(_getState) { data ->
                        _typeOfServiceList.value = commonUiMapper.typeOfServiceListToUi(data)
                    }
                }

            subscriberRepository.listSubscriber()
                .asNetworkResult()
                .collect {
                    it.asGetState(_getState) { data ->
                        _subscriberList.value = commonUiMapper.subscriberListToUi(data)
                    }
                }
        }
    }

    fun addMeter(file: Bitmap) {
        val selectedSubscriberId = apartmentList.value?.find { it.id == selectedApartmentId }?.subscriberId
        val selectedManagementCompanyId = subscriberList.value?.find { it.id == selectedSubscriberId }?.managementCompanyId

        if (selectedManagementCompanyId !== null && selectedSubscriberId !== null) {
            viewModelScope.launch {
                appealRepository.addMeter(
                    appeal = appealMapper.addToRemote(
                        AppealAddMeterUiModel(
                            id = null,
                            apartmentId = selectedApartmentId,
                            typeOfServiceId = selectedTypeOfServiceId,
                            factoryNumber = selectedFactoryNumber,
                            verifiedAt = selectVerifiedAt!!,
                            issuedAt = selectIssuedAt!!,
                            managementCompanyId = selectedManagementCompanyId,
                            subscriberId = selectedSubscriberId,
                        )
                    ),
                    file = file
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
