package com.example.myhome.features.event.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myhome.features.event.EventApiService
import com.example.myhome.features.event.dto.NotificationAndVotingListItemResponse
import com.example.myhome.features.event.dto.VotingUpdateRequest
import com.example.myhome.features.event.pagingsources.EventPagingSourceFactory
import com.example.myhome.models.FilterListItemRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EventRepositoryImpl(
    private val eventApiService: EventApiService,
    private val eventPagingSourceFactory: EventPagingSourceFactory,
    private val pageConfig: PagingConfig
): EventRepository {
    override fun listEvent(
        filters: List<FilterListItemRequest>?
    ): Flow<PagingData<NotificationAndVotingListItemResponse>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = { eventPagingSourceFactory.create(filters) }
        ).flow
    }

    override fun updateVoting(vote: VotingUpdateRequest): Flow<Boolean> = flow {
        eventApiService.updateVoting(vote)
        emit(true)
    }
}