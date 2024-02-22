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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
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
            _meterList.value = setIsIssued(meterUiMapper.mapMeterListToUi(it))
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

    private fun fetchMeterList() {
        viewModelScope.launch {
            apartmentWithMeterListUseCase()
                .flowOn(Dispatchers.IO)
                .catch {
                    _meterList.value = listOf()
                    _apartmentList.value = listOf()
                }
                .collect {
                    if (it.isNotEmpty()) {
                        setupLists(it)
                    } else {
                        TODO("Ошибка!")
                    }
                }
        }
    }

    fun setupLists(list: List<ApartmentWithMeterGetModel>) {
        apartmentAndMeterList = list

        val fstApartment = list.first()
        selectedApartmentId = fstApartment.id

        _apartmentList.value = setSelected(meterUiMapper.mapApartmentListToUi(list))
        _meterList.value = setIsIssued(meterUiMapper.mapMeterListToUi(fstApartment))
    }
}
