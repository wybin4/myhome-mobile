package com.example.myhome.common.storages

import com.example.myhome.common.dtos.SubscriberGetDto
import com.example.myhome.common.dtos.SubscriberListDto
import com.example.myhome.common.services.SubscriberApiService

class SubscriberRemoteStorage(private val subscriberApiService: SubscriberApiService): SubscriberStorage {
    override suspend fun listSubscriber(): List<SubscriberGetDto> {
        val request = SubscriberListDto(userId = 1)
        return subscriberApiService.listSubscriber(request)
    }
}