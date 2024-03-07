package com.example.myhome.features.event

import com.example.myhome.features.event.dto.EventListResponse
import com.example.myhome.features.event.dto.EventListRequest
import com.example.myhome.features.event.dto.VoteListItemResponse
import com.example.myhome.features.event.dto.VotingUpdateRequest
import com.example.myhome.features.event.models.EventType

class EventStorage(
    private val eventApiService: EventApiService
) {
    suspend fun listEvent(): EventListResponse {
        return eventApiService.listEvent(
            EventListRequest(
                userId = 1,
                events = listOf(EventType.Notification, EventType.Voting)
            )
        )
    }

    suspend fun updateVoting(vote: VotingUpdateRequest): Boolean {
        val result = eventApiService.updateVoting(vote)
        if (result != null) {
            return true
        }
        return false
    }

}