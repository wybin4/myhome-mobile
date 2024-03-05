package com.example.myhome.event.storages

import com.example.myhome.event.dto.EventGetDto
import com.example.myhome.event.dto.EventListDto
import com.example.myhome.event.models.EventType
import com.example.myhome.event.services.EventApiService

class EventRemoteStorage(
    private val eventApiService: EventApiService
): EventStorage {
    override suspend fun listEvent(): EventGetDto {
        return eventApiService.listEvent(
            EventListDto(
                userId = 1,
                events = listOf(EventType.Notification, EventType.Voting)
            )
        )
    }
}