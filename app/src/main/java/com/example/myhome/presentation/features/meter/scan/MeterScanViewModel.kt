package com.example.myhome.presentation.features.meter.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.features.meter.models.ReadingAddModel
import com.example.myhome.features.meter.usecases.ReadingAddUseCase
import com.example.myhome.presentation.features.meter.MeterGetToScanParcelableModel
import com.example.myhome.presentation.models.AddResource
import com.example.myhome.presentation.models.asAddResource
import com.example.myhome.presentation.models.asNetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MeterScanViewModel @Inject constructor(
    private val readingAddUseCase: ReadingAddUseCase
) : ViewModel() {
    lateinit var meterParcelable : MeterGetToScanParcelableModel

    private val _dataAddState = MutableLiveData<AddResource>(AddResource.None)
    val dataAddState: LiveData<AddResource> = _dataAddState

    fun addMeterReading(newReading: Double) {
        viewModelScope.launch {
            readingAddUseCase(
                ReadingAddModel(
                    meterId = meterParcelable.meterId,
                    reading = newReading,
                    readAt = Date()
                )
            )
                .asNetworkResult()
                .collect {
                    it.asAddResource(_dataAddState)
                }
        }
    }
}