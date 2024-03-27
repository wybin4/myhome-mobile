package com.example.myhome.features.appeal

import com.example.myhome.features.event.EventApiService
import com.example.myhome.features.event.dto.EventListRequest
import com.example.myhome.features.event.models.EventTypeRequest
import com.example.myhome.features.CustomPagingSource
import com.example.myhome.models.MetaRequest

class AppealPagingSource(private val eventApiService: EventApiService) :
    CustomPagingSource<AppealListItemResponse>() {
    companion object {
        const val APPEAL_PAGE_SIZE = 10
    }

    override suspend fun invoke(page: Int): List<AppealListItemResponse> {
        val events = eventApiService.listEvent(
            EventListRequest(
                userId = 1,
                eventType = EventTypeRequest.Appeal,
                meta = MetaRequest(page, APPEAL_PAGE_SIZE)
            )
        )
        return events.appeals
    }

}