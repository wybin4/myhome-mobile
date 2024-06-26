package com.example.myhome.presentation.features.appeal.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.appeal.models.AppealType
import com.example.myhome.features.common.repositories.ApartmentRepository
import com.example.myhome.features.common.repositories.SubscriberRepository
import com.example.myhome.presentation.features.appeal.AppealProblemOrClaimUiModel
import com.example.myhome.presentation.features.common.CommonUiConverter
import com.example.myhome.presentation.features.common.models.ApartmentExtendedUiModel
import com.example.myhome.presentation.features.common.models.SubscriberUiModel
import com.example.myhome.presentation.models.asNetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

abstract class BaseAppealProblemOrClaimViewModel(
    private val apartmentRepository: ApartmentRepository,
    private val subscriberRepository: SubscriberRepository,
    private val commonUiConverter: CommonUiConverter,
    appealType: AppealType
) : BaseAppealViewModel(appealType) {
    private val _apartmentList = MutableLiveData<List<ApartmentExtendedUiModel>>()
    val apartmentList: LiveData<List<ApartmentExtendedUiModel>> = _apartmentList

    private val _subscriberList = MutableLiveData<List<SubscriberUiModel>>()
    private val subscriberList: LiveData<List<SubscriberUiModel>> = _subscriberList

    var selectedText = ""
    var selectedApartmentId = -1

    init {
        fetchLists()
    }

    private fun fetchLists() {
        viewModelScope.launch {
            apartmentRepository.listApartment()
                .flowOn(Dispatchers.IO)
                .catch {
                    _apartmentList.value = listOf()
                }
                .collect {
                    _apartmentList.value = commonUiConverter.apartmentListToUi(it)
                }

            subscriberRepository.listSubscriber()
                .flowOn(Dispatchers.IO)
                .catch {
                    _subscriberList.value = listOf()
                }
                .collect {
                    _subscriberList.value = commonUiConverter.subscriberListToUi(it)
                }
        }
    }

    protected abstract fun pickUseCase(appeal: AppealProblemOrClaimUiModel): Flow<Boolean>

    fun claim() {
        val selectedSubscriberId = apartmentList.value?.find { it.id == selectedApartmentId }?.subscriberId
        val selectedManagementCompanyId = subscriberList.value?.find { it.id == selectedSubscriberId }?.managementCompanyId

        if (selectedManagementCompanyId !== null && selectedSubscriberId !== null) {
            viewModelScope.launch {
                pickUseCase(
                    AppealProblemOrClaimUiModel(
                        managementCompanyId = selectedManagementCompanyId,
                        subscriberId = selectedSubscriberId,
                        text = selectedText
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
