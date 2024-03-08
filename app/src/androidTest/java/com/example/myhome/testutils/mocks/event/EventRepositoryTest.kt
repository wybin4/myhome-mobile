package com.example.myhome.testutils.mocks.event

import com.example.myhome.features.event.EventRepository
import com.example.myhome.features.event.models.EventListModel
import com.example.myhome.features.event.models.VotingUpdateModel
import com.example.myhome.presentation.testutils.providers.EventUITestListProvider.eventList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class EventRepositoryTest : EventRepository {
    override fun listEvent(): Flow<EventListModel> {
        return flowOf(eventList)
    }

    override fun updateVoting(vote: VotingUpdateModel): Flow<Boolean> {
        return flowOf(true)
    }

}
