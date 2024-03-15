package com.example.myhome.features.event.repositories

import com.example.myhome.features.event.dto.EventListResponse
import com.example.myhome.features.event.dto.VotingUpdateRequest
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun listEvent(): Flow<EventListResponse>
    fun updateVoting(vote: VotingUpdateRequest): Flow<Boolean>

}