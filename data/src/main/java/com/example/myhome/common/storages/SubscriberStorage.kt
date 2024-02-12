package com.example.myhome.common.storages

import com.example.myhome.common.dtos.SubscriberGetDto

interface SubscriberStorage {
    suspend fun listSubscriber(): List<SubscriberGetDto>
}