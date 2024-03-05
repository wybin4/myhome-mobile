package com.example.myhome.event.repositories

import com.example.myhome.event.mappers.EventRemoteMapper
import com.example.myhome.event.models.EventGetModel
import com.example.myhome.event.storages.EventStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EventRepositoryImpl(
    private val eventStorage: EventStorage,
    private val eventRemoteMapper: EventRemoteMapper
): EventRepository {
    override fun listEvent(): Flow<EventGetModel> = flow {
        val result = eventStorage.listEvent()
        emit(eventRemoteMapper.mapListToDomain(result))
    }
}