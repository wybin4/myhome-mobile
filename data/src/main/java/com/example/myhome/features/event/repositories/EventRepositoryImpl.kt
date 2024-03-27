package com.example.myhome.features.event.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myhome.features.event.EventApiService
import com.example.myhome.features.event.EventPagingSource
import com.example.myhome.features.event.dto.NotificationAndVotingListResponse
import com.example.myhome.features.event.dto.VotingUpdateRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EventRepositoryImpl(
    private val eventApiService: EventApiService,
    private val eventPagingSource: EventPagingSource,
    private val pageConfig: PagingConfig
): EventRepository {
    override fun listEvent(): Flow<PagingData<NotificationAndVotingListResponse>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = { eventPagingSource }
        ).flow
    }

    override fun updateVoting(vote: VotingUpdateRequest): Flow<Boolean> = flow {
        eventApiService.updateVoting(vote)
        emit(true)
    }
}