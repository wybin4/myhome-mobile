package com.example.myhome.features.common.repositories

import com.example.myhome.features.common.dtos.SubscriberListItemResponse
import kotlinx.coroutines.flow.Flow

interface SubscriberRepository {
    fun listSubscriber(): Flow<List<SubscriberListItemResponse>>
}