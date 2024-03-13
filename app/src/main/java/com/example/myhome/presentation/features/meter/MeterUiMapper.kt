package com.example.myhome.presentation.features.meter

import com.example.myhome.presentation.features.meter.converters.MeterParcelableConverter
import com.example.myhome.presentation.features.meter.converters.MeterUiConverter
import javax.inject.Inject

class MeterUiMapper @Inject constructor(): MeterParcelableConverter, MeterUiConverter