package com.example.myhome.features.common.repositories

import com.example.myhome.features.common.models.SubscriberListItemModel
import kotlinx.coroutines.flow.Flow

interface SubscriberRepository {
    fun listSubscriber(): Flow<List<SubscriberListItemModel>>
}