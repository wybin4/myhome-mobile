package com.example.myhome.presentation.features.event

import com.example.myhome.features.event.models.EventListModel
import javax.inject.Inject

class EventUiMapper @Inject constructor(): EventUiConverter {
    fun mapListToUi(events: EventListModel): List<EventUiModel> {
        return eventListToUi(events)
    }

}