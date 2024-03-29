package com.example.myhome.features.event

import com.example.myhome.features.event.dto.EventListRequest
import com.example.myhome.features.event.dto.NotificationAndVotingListResponse
import com.example.myhome.features.event.models.EventTypeRequest
import com.example.myhome.features.CustomPagingSource
import com.example.myhome.models.MetaRequest

class EventPagingSource(
    private val eventApiService: EventApiService
) : CustomPagingSource<NotificationAndVotingListResponse>() {
    companion object {
        const val EVENT_PAGE_SIZE = 5
    }

    override suspend fun invoke(page: Int): List<NotificationAndVotingListResponse> {
        val events = eventApiService.listEvent(
            EventListRequest(
                userId = 1,
                eventType = EventTypeRequest.NotificationAndVoting,
                meta = MetaRequest(page, EVENT_PAGE_SIZE)
            )
        )
        return events.notificationsAndVotings
    }

}