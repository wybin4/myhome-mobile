package com.example.myhome.presentation.meter.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.meter.usecases.ApartmentWithMeterListUseCase
import com.example.myhome.utils.mappers.MeterUiMapper
import com.example.myhome.utils.models.ApartmentUiModel
import com.example.myhome.utils.models.MeterListToGetParcelableModel
import com.example.myhome.utils.models.MeterUiModel
import com.example.myhome.utils.models.NetworkResult
import com.example.myhome.utils.models.Resource
import com.example.myhome.utils.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeterListViewModel @Inject constructor(
    private val apartmentWithMeterListUseCase: ApartmentWithMeterListUseCase,
    private val meterUiMapper: MeterUiMapper
) : ViewModel() {
    private var apartmentAndMeterList: List<ApartmentWithMeterGetModel> = listOf()

    private val _apartmentList = MutableLiveData<List<ApartmentUiModel>>()
    val apartmentList: LiveData<List<ApartmentUiModel>> = _apartmentList

    private val _meterList = MutableLiveData<List<MeterUiModel>>()
    val meterList: LiveData<List<MeterUiModel>> = _meterList

    private val _meterListState = MutableLiveData<Resource>(Resource.Loading)
    val meterListState: LiveData<Resource> = _meterListState

    var selectedApartmentId: Int = -1

    init {
        fetchMeterList()
    }

    fun mapMeterUiToGetParcel(meter: MeterUiModel): MeterListToGetParcelableModel {
        return meterUiMapper.mapMeterUiToGetParcel(meter)
    }

    fun changeSelectedApartment(apartment: ApartmentUiModel) {
        selectedApartmentId = apartment.id
        _apartmentList.value = _apartmentList.value?.let { setSelected(it) }
        val selectedApartment = apartmentAndMeterList.find { it.id == selectedApartmentId }

        selectedApartment?.let {
            _meterList.value = setIsIssued(meterUiMapper.mapApartmentWithMeterToUi(it))
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
            apartmentWithMeterListUseCase()
                .asNetworkResult()
                .collect { result ->
                    when (result) {
                        is NetworkResult.Success -> {
                            val data = result.data
                            if (data.isNotEmpty()) {
                                _meterListState.value = Resource.Success
                                setupLists(data)
                            } else {
                                _meterListState.value = Resource.Empty
                            }
                        }
                        is NetworkResult.Loading -> {
                            _meterListState.value = Resource.Loading
                        }
                        is NetworkResult.Error -> {
                            val errorMessage = result.exception.message
                            if (errorMessage != null) {
                                _meterListState.value = Resource.Error(errorMessage)
                            }
                        }
                    }
                }
        }

    }

    fun setupLists(list: List<ApartmentWithMeterGetModel>) {
        apartmentAndMeterList = list

        val fstApartment = list.first()
        selectedApartmentId = fstApartment.id

        _apartmentList.value = setSelected(meterUiMapper.mapApartmentListToUi(list))
        _meterList.value = setIsIssued(meterUiMapper.mapApartmentWithMeterToUi(fstApartment))
    }
}
