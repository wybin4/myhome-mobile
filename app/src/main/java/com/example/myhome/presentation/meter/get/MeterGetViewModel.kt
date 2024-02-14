package com.example.myhome.presentation.meter.get

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.meter.models.ReadingGetModel
import com.example.myhome.meter.usecases.ReadingListUseCase
import com.example.myhome.presentation.models.MeterListToGetParcelableModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MeterGetViewModel @Inject constructor(
    private val readingListUseCase: ReadingListUseCase
) : ViewModel() {
    private val _readingList = MutableLiveData<List<ReadingGetModel>>()
    val readingList: LiveData<List<ReadingGetModel>> = _readingList

    lateinit var meterParcelable : MeterListToGetParcelableModel

    fun fetchReadingList() {
        if (meterParcelable !== null) {
            viewModelScope.launch {
                readingListUseCase(meterParcelable.id)
                    .flowOn(Dispatchers.IO)
                    .catch {
                        _readingList.value = listOf()
                    }
                    .collect {
                        _readingList.value = it
                    }
            }
        } else {
            TODO("Ошибка!")
        }
    }
}
