package com.example.myhome.presentation.meter.scan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myhome.meter.models.ReadingAddModel
import com.example.myhome.meter.usecases.ReadingAddUseCase
import com.example.myhome.utils.models.MeterGetToScanParcelableModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class MeterScanViewModel @Inject constructor(
    private val readingAddUseCase: ReadingAddUseCase
) : ViewModel() {
    lateinit var meterParcelable : MeterGetToScanParcelableModel
    fun addMeterReading(newReading: Double) {
        viewModelScope.launch {
            readingAddUseCase(
                ReadingAddModel(
                    meterId = meterParcelable.meterId,
                    reading = newReading,
                    readAt = Date()
                )
            )
        }
    }
}