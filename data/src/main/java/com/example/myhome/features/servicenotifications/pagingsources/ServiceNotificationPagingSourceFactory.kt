package com.example.myhome.features.servicenotifications.pagingsources

import com.example.myhome.features.CustomPagingSource
import com.example.myhome.features.event.EventApiService
import com.example.myhome.features.event.dto.ServiceNotificationListItemResponse

class ServiceNotificationPagingSourceFactory(private val eventApiService: EventApiService) {
    fun create(): CustomPagingSource<ServiceNotificationListItemResponse> {
        return ServiceNotificationPagingSource(eventApiService)
    }
}