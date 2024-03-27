package com.example.myhome.features.event.repositories

import androidx.paging.PagingData
import com.example.myhome.features.event.dto.NotificationAndVotingListResponse
import com.example.myhome.features.event.dto.VotingUpdateRequest
import kotlinx.coroutines.flow.Flow

interface EventRepository {
    fun listEvent(): Flow<PagingData<NotificationAndVotingListResponse>>
    fun updateVoting(vote: VotingUpdateRequest): Flow<Boolean>

}