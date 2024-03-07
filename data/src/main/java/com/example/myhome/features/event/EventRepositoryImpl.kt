package com.example.myhome.features.event

import com.example.myhome.features.event.models.EventListModel
import com.example.myhome.features.event.models.VotingUpdateModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EventRepositoryImpl(
    private val eventStorage: EventStorage,
    private val eventRemoteMapper: EventRemoteMapper
): EventRepository {
    override fun listEvent(): Flow<EventListModel> = flow {
        val result = eventStorage.listEvent()
        emit(eventRemoteMapper.mapListToDomain(result))
    }

    override fun updateVoting(vote: VotingUpdateModel): Flow<Boolean> = flow {
        val dto = eventRemoteMapper.updateToRemote(vote)
        emit(eventStorage.updateVoting(dto))
    }
}