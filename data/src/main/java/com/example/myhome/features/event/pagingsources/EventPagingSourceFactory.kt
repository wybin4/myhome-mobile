package com.example.myhome.features.event.pagingsources

import com.example.myhome.features.CustomPagingSource
import com.example.myhome.features.event.EventApiService
import com.example.myhome.features.event.dto.NotificationAndVotingListItemResponse
import com.example.myhome.models.FilterListItemRequest

class EventPagingSourceFactory(private val eventApiService: EventApiService) {
    fun create(filters: List<FilterListItemRequest>?): CustomPagingSource<NotificationAndVotingListItemResponse> {
        return EventPagingSource(filters, eventApiService)
    }
}