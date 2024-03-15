package com.example.myhome.presentation.features.event

import com.example.myhome.presentation.features.event.converters.EventRemoteConverter
import com.example.myhome.presentation.features.event.converters.EventUiConverter
import javax.inject.Inject

class EventMapper @Inject constructor(): EventUiConverter, EventRemoteConverter