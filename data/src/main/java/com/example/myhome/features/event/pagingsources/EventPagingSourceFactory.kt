package com.example.myhome.features.event.pagingsources

import com.example.myhome.features.CustomPagingSource
import com.example.myhome.features.event.EventApiService
import com.example.myhome.features.event.dto.NotificationAndVotingListResponse
import com.example.myhome.models.FilterListItemRequest

class EventPagingSourceFactory(private val eventApiService: EventApiService) {
    fun create(filters: List<FilterListItemRequest>?): CustomPagingSource<NotificationAndVotingListResponse> {
        return EventPagingSource(filters, eventApiService)
    }
}