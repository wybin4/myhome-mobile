package com.example.myhome.presentation.appeal.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myhome.appeal.models.AppealProblemOrClaimModel
import com.example.myhome.appeal.models.AppealType
import com.example.myhome.common.models.ApartmentGetModel
import com.example.myhome.common.models.SubscriberGetModel
import com.example.myhome.common.usecases.ApartmentListUseCase
import com.example.myhome.common.usecases.SubscriberListUseCase
import com.example.myhome.utils.models.NetworkResult
import com.example.myhome.utils.models.Resource
import com.example.myhome.utils.models.asNetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

abstract class BaseAppealProblemOrClaimViewModel(
    private val apartmentListUseCase: ApartmentListUseCase,
    private val subscriberListUseCase: SubscriberListUseCase,
    appealType: AppealType
) : BaseAppealViewModel(appealType) {
    private val _apartmentList = MutableLiveData<List<ApartmentGetModel>>()
    val apartmentList: LiveData<List<ApartmentGetModel>> = _apartmentList

    private val _subscriberList = MutableLiveData<List<SubscriberGetModel>>()
    private val subscriberList: LiveData<List<SubscriberGetModel>> = _subscriberList

    var selectedText = ""
    var selectedApartmentId = -1

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

    protected abstract fun pickUseCase(appeal: AppealProblemOrClaimModel): Flow<Boolean>

    fun claim() {
        val selectedSubscriberId = apartmentList.value?.find { it.id == selectedApartmentId }?.subscriberId
        val selectedManagementCompanyId = subscriberList.value?.find { it.subscriberId == selectedSubscriberId }?.managementCompanyId

        if (selectedManagementCompanyId !== null && selectedSubscriberId !== null) {
            viewModelScope.launch {
                pickUseCase(
                    AppealProblemOrClaimModel(
                        managementCompanyId = selectedManagementCompanyId,
                        subscriberId = selectedSubscriberId,
                        text = selectedText
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
