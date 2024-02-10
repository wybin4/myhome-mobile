package com.example.myhome.presentation.meter.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.meter.models.MeterGetModel
import com.example.myhome.meter.usecases.MeterListUseCase
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
    private val _meterList = MutableLiveData<List<MeterGetModel>>()
    val meterList: LiveData<List<MeterGetModel>> = _meterList

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
                    _meterList.value = it
                }
        }
    }
}
