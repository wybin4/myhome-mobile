package com.example.myhome.presentation.features.meter.mappers

import com.example.myhome.presentation.features.meter.converters.MeterParcelableConverter
import com.example.myhome.presentation.features.meter.converters.MeterUiConverter
import com.example.myhome.presentation.features.meter.converters.ReadingRemoteConverter
import com.example.myhome.presentation.features.meter.converters.ReadingUiConverter
import javax.inject.Inject

class ReadingMapper @Inject constructor(): ReadingUiConverter, ReadingRemoteConverter