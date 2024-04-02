package com.example.myhome.presentation.features.meter.update

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.appeal.repositories.AppealRepository
import com.example.myhome.features.common.repositories.ApartmentRepository
import com.example.myhome.features.common.repositories.SubscriberRepository
import com.example.myhome.presentation.features.appeal.AppealMapper
import com.example.myhome.presentation.features.appeal.AppealUpdateMeterUiModel
import com.example.myhome.presentation.features.appeal.add.verify.BaseAppealVerifyViewModel
import com.example.myhome.presentation.features.common.CommonUiConverter
import com.example.myhome.presentation.features.common.models.ApartmentExtendedUiModel
import com.example.myhome.presentation.features.meter.MeterGetToUpdateParcelableModel
import com.example.myhome.presentation.models.asGetState
import com.example.myhome.presentation.models.asNetworkResult
import com.example.myhome.presentation.utils.mappers.ImageMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeterUpdateViewModel @Inject constructor(
    subscriberRepository: SubscriberRepository,
    private val apartmentRepository: ApartmentRepository,
    private val appealRepository: AppealRepository,
    private val appealMapper: AppealMapper,
    private val commonUiConverter: CommonUiConverter,
    val imageMapper: ImageMapper
) : BaseAppealVerifyViewModel(subscriberRepository, commonUiConverter) {
    private val _apartmentList = MutableLiveData<List<ApartmentExtendedUiModel>>()
    private val apartmentList: LiveData<List<ApartmentExtendedUiModel>> = _apartmentList

    lateinit var meterParcelable : MeterGetToUpdateParcelableModel

    init {
        viewModelScope.launch {
            fetchApartmentList()
            fetchSubscriberList()
        }
    }

    private suspend fun fetchApartmentList() {
        apartmentRepository.listApartment()
            .asNetworkResult()
            .collect {
                it.asGetState(mutableGetState) { data ->
                    _apartmentList.value = commonUiConverter.apartmentListToUi(data)
                }
            }
    }

    fun updateMeter(file: Bitmap) {
        val selectedSubscriberId = apartmentList.value?.find { it.id == meterParcelable.apartmentId }?.id
        val selectedManagementCompanyId = subscriberList.value?.find { it.id == selectedSubscriberId }?.managementCompanyId

        if (selectedManagementCompanyId !== null && selectedSubscriberId !== null) {
            viewModelScope.launch {
                appealRepository.updateMeter(
                    appeal = appealMapper.updateToRemote(
                        AppealUpdateMeterUiModel(
                            meterId = meterParcelable.meterId,
                            verifiedAt = selectVerifiedAt!!,
                            issuedAt = selectIssuedAt!!,
                            managementCompanyId = selectedManagementCompanyId,
                            subscriberId = selectedSubscriberId
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
