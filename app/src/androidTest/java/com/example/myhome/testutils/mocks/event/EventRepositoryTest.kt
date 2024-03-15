package com.example.myhome.testutils.mocks.event

import com.example.myhome.features.event.dto.EventListResponse
import com.example.myhome.features.event.dto.VotingUpdateRequest
import com.example.myhome.features.event.repositories.EventRepository
import com.example.myhome.presentation.testutils.providers.EventUITestListProvider.eventList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class EventRepositoryTest : EventRepository {
    override fun listEvent(): Flow<EventListResponse> {
        return flowOf(eventList)
    }

    override fun updateVoting(vote: VotingUpdateRequest): Flow<Boolean> {
        return flowOf(true)
    }

}
