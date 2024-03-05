package com.example.myhome.event.repositories

import com.example.myhome.event.models.EventGetModel
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun listEvent(): Flow<EventGetModel>
}