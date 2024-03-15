package com.example.myhome.presentation.features.meter.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.meter.dtos.ApartmentWithMeterListItemResponse
import com.example.myhome.features.meter.repositories.MeterRepository
import com.example.myhome.presentation.features.meter.ApartmentUiModel
import com.example.myhome.presentation.features.meter.MeterListToGetParcelableModel
import com.example.myhome.presentation.features.meter.mappers.MeterMapper
import com.example.myhome.presentation.features.meter.MeterUiModel
import com.example.myhome.presentation.models.Resource
import com.example.myhome.presentation.models.asListResource
import com.example.myhome.presentation.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeterListViewModel @Inject constructor(
    private val meterRepository: MeterRepository,
    private val meterMapper: MeterMapper
) : ViewModel() {
    private var apartmentAndMeterList: List<ApartmentWithMeterListItemResponse> = listOf()

    private val _apartmentList = MutableLiveData<List<ApartmentUiModel>>()
    val apartmentList: LiveData<List<ApartmentUiModel>> = _apartmentList

    private val _meterList = MutableLiveData<List<MeterUiModel>>()
    val meterList: LiveData<List<MeterUiModel>> = _meterList

    private val _meterListState = MutableLiveData<Resource>(Resource.Loading)
    val meterListState: LiveData<Resource> = _meterListState

    var selectedApartmentId: Int = -1

    fun mapMeterUiToGetParcel(meter: MeterUiModel): MeterListToGetParcelableModel {
        return meterMapper.meterUiToGetParcel(meter)
    }

    fun changeSelectedApartment(apartment: ApartmentUiModel) {
        selectedApartmentId = apartment.id
        _apartmentList.value = _apartmentList.value?.let { setSelected(it) }
        val selectedApartment = apartmentAndMeterList.find { it.apartmentId == selectedApartmentId }

        selectedApartment?.let {
            _meterList.value = setIsIssued(meterMapper.apartmentWithMeterToUi(it))
        } ?: run {
            _meterList.value = emptyList()
        }
    }

    private fun setIsIssued(meters: List<MeterUiModel>): List<MeterUiModel> {
        return meters.map { meter ->
            meter.setIsIssued()
        }
    }

    private fun setSelected(apartments: List<ApartmentUiModel>): List<ApartmentUiModel> {
        return apartments.map { apartment ->
            apartment.setSelected(selectedApartmentId)
        }
    }

    fun fetchMeterList() {
        viewModelScope.launch {
            meterRepository.listApartmentWithMeter()
                .asNetworkResult()
                .collect {
                    it.asListResource(_meterListState) { data ->
                        setupLists(data)
                    }
                }
        }

    }

    fun setupLists(list: List<ApartmentWithMeterListItemResponse>) {
        apartmentAndMeterList = list

        val fstApartment = list.first()
        selectedApartmentId = fstApartment.apartmentId

        _apartmentList.value = setSelected(meterMapper.apartmentListToUi(list))
        _meterList.value = setIsIssued(meterMapper.apartmentWithMeterToUi(fstApartment))
    }
}
