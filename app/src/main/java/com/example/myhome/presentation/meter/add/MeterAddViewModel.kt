package com.example.myhome.presentation.meter.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.common.usecases.ApartmentListUseCase
import com.example.myhome.common.usecases.TypeOfServiceListUseCase
import com.example.myhome.common.models.ApartmentGetModel
import com.example.myhome.common.models.TypeOfServiceGetModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeterAddViewModel @Inject constructor(
    private val apartmentListUseCase: ApartmentListUseCase,
    private val typeOfServiceListUseCase: TypeOfServiceListUseCase
) : ViewModel() {
    private val _apartmentList = MutableLiveData<List<ApartmentGetModel>>()
    val apartmentList: LiveData<List<ApartmentGetModel>> = _apartmentList

    private val _typeOfServiceList = MutableLiveData<List<TypeOfServiceGetModel>>()
    val typeOfServiceList: LiveData<List<TypeOfServiceGetModel>> = _typeOfServiceList

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

            typeOfServiceListUseCase()
                .flowOn(Dispatchers.IO)
                .catch {
                    _typeOfServiceList.value = listOf()
                }
                .collect {
                    _typeOfServiceList.value = it
                }
        }
    }
}
