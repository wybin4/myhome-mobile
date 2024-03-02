package com.example.myhome.presentation.meter.update

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myhome.appeal.models.AppealUpdateMeterModel
import com.example.myhome.appeal.usecases.AppealAddUseCase
import com.example.myhome.common.models.ApartmentGetModel
import com.example.myhome.common.usecases.ApartmentListUseCase
import com.example.myhome.common.usecases.SubscriberListUseCase
import com.example.myhome.presentation.appeal.add.verify.BaseAppealVerifyViewModel
import com.example.myhome.utils.mappers.ImageMapper
import com.example.myhome.utils.models.MeterGetToUpdateParcelableModel
import com.example.myhome.utils.models.NetworkResult
import com.example.myhome.utils.models.Resource
import com.example.myhome.utils.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeterUpdateViewModel @Inject constructor(
    subscriberListUseCase: SubscriberListUseCase,
    private val apartmentListUseCase: ApartmentListUseCase,
    private val appealAddUseCase: AppealAddUseCase,
    private val imageMapper: ImageMapper
) : BaseAppealVerifyViewModel(subscriberListUseCase) {
    private val _apartmentList = MutableLiveData<List<ApartmentGetModel>>()
    private val apartmentList: LiveData<List<ApartmentGetModel>> = _apartmentList

    lateinit var meterParcelable : MeterGetToUpdateParcelableModel

    init {
        viewModelScope.launch {
            fetchApartmentList()
            fetchSubscriberList()
        }
    }

    private suspend fun fetchApartmentList() {
        apartmentListUseCase()
            .asNetworkResult()
            .collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        val data = result.data
                        if (data.isNotEmpty()) {
                            setDataGetState(Resource.Success)
                            _apartmentList.value = data
                        } else {
                            setDataGetState(Resource.Empty)
                        }
                    }
                    is NetworkResult.Loading -> {
                        setDataGetState(Resource.Loading)
                    }
                    is NetworkResult.Error -> {
                        val errorMessage = result.exception.message
                        if (errorMessage != null) {
                            setDataGetState(Resource.Error(errorMessage))
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
                        manageState(result)
                    }
            }
        } else {
            codeError()
        }
    }
}
