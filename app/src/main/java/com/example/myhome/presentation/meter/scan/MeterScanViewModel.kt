package com.example.myhome.presentation.meter.scan

import androidx.lifecycle.ViewModel
import com.example.myhome.presentation.models.MeterGetToScanParcelableModel
import com.example.myhome.presentation.models.MeterListToGetParcelableModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MeterScanViewModel @Inject constructor() : ViewModel() {
    lateinit var meterParcelable : MeterGetToScanParcelableModel

}