package com.example.myhome.utils.mappers

import com.example.myhome.event.models.EventGetModel
import com.example.myhome.utils.converters.EventUiConverter
import com.example.myhome.utils.models.EventUiModel
import javax.inject.Inject

class EventUiMapper @Inject constructor(): EventUiConverter {
    fun mapListToUi(events: EventGetModel): List<EventUiModel> {
        return eventListToUi(events)
    }

}