package com.example.myhome.features.common.storages

import com.example.myhome.features.common.CommonApiService
import com.example.myhome.features.common.dtos.SubscriberListItemResponse
import com.example.myhome.features.common.dtos.SubscriberListRequest

class SubscriberStorage(private val commonApiService: CommonApiService) {
     suspend fun listSubscriber(): List<SubscriberListItemResponse> {
        val request = SubscriberListRequest(userId = 1)
        return commonApiService.listSubscriber(request)
    }
}