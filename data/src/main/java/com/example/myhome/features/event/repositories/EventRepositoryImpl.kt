package com.example.myhome.features.event.repositories

import com.example.myhome.features.event.EventApiService
import com.example.myhome.features.event.dto.EventListRequest
import com.example.myhome.features.event.dto.EventListResponse
import com.example.myhome.features.event.dto.VotingUpdateRequest
import com.example.myhome.features.event.models.EventType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EventRepositoryImpl(
    private val eventApiService: EventApiService
): EventRepository {
    override fun listEvent(): Flow<EventListResponse> = flow {
        emit(
            eventApiService.listEvent(
            EventListRequest(
                userId = 1,
                events = listOf(EventType.Notification, EventType.Voting)
            )
        )
        )
    }

    override fun updateVoting(vote: VotingUpdateRequest): Flow<Boolean> = flow {
        val result = eventApiService.updateVoting(vote)
        if (result != null) {
            emit(true)
        }
        emit(false)
    }
}