package com.example.myhome.features.event

import com.example.myhome.features.event.models.EventListModel
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun listEvent(): Flow<EventListModel>
}