package com.example.myhome.presentation.features.meter.mappers

import com.example.myhome.presentation.features.meter.converters.MeterParcelableConverter
import com.example.myhome.presentation.features.meter.converters.MeterUiConverter
import javax.inject.Inject

class MeterMapper @Inject constructor(): MeterParcelableConverter, MeterUiConverter