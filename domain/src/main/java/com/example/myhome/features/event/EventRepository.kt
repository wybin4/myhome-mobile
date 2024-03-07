package com.example.myhome.features.event

import com.example.myhome.features.event.models.EventListModel
import com.example.myhome.features.event.models.VotingUpdateModel
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun listEvent(): Flow<EventListModel>
    fun updateVoting(vote: VotingUpdateModel): Flow<Boolean>

}