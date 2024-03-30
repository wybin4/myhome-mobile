package com.example.myhome.features.event

import com.example.myhome.features.CustomPagingSource
import com.example.myhome.features.event.dto.EventListRequest
import com.example.myhome.features.event.dto.NotificationAndVotingListResponse
import com.example.myhome.features.event.models.EventTypeRequest
import com.example.myhome.models.FilterListItemRequest
import com.example.myhome.models.MetaRequest

class EventPagingSourceFactory(private val eventApiService: EventApiService) {
    fun create(filters: List<FilterListItemRequest>?): CustomPagingSource<NotificationAndVotingListResponse> {
        return EventPagingSource(filters, eventApiService)
    }
}

class EventPagingSource(
    private val filters: List<FilterListItemRequest>?
    , private val eventApiService: EventApiService
) : CustomPagingSource<NotificationAndVotingListResponse>() {
    companion object {
        const val EVENT_PAGE_SIZE = 5
    }

    override suspend fun invoke(page: Int): List<NotificationAndVotingListResponse> {
        val events = eventApiService.listEvent(
            EventListRequest(
                userId = 1,
                eventType = EventTypeRequest.NotificationAndVoting,
                meta = MetaRequest(page, EVENT_PAGE_SIZE, filters)
            )
        )
        return events.notificationsAndVotings
    }

}