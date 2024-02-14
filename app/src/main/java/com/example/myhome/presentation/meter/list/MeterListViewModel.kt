package com.example.myhome.presentation.meter.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.meter.usecases.MeterListUseCase
import com.example.myhome.presentation.models.MeterUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeterListViewModel @Inject constructor(
    private val meterListUseCase: MeterListUseCase
) : ViewModel() {
    private val _meterList = MutableLiveData<List<MeterUiModel>>()
    val meterList: LiveData<List<MeterUiModel>> = _meterList

    init {
        fetchMeterList()
    }

    private fun fetchMeterList() {
        viewModelScope.launch {
            meterListUseCase()
                .flowOn(Dispatchers.IO)
                .catch {
                    _meterList.value = listOf()
                }
                .collect {

                    val list = it.map { meter ->
                        MeterUiModel(
                            id = meter.id,
                            factoryNumber = meter.factoryNumber,
                            verifiedAt = meter.verifiedAt,
                            issuedAt = meter.issuedAt,
                            currentReading = meter.currentReading,
                            typeOfServiceName = meter.typeOfServiceName,
                            unitName = meter.unitName,
                            apartmentId = meter.apartmentId,
                            address = "пер. Соборный 99, кв. 12",
                            isIssued = false
                        ).setIsIssued()
                    }

                    _meterList.value = list
                }
        }
    }
}
