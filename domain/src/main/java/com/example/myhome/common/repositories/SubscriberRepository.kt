package com.example.myhome.common.repositories

import com.example.myhome.common.models.SubscriberGetModel
import kotlinx.coroutines.flow.Flow

interface SubscriberRepository {
    fun listSubscriber(): Flow<List<SubscriberGetModel>>
}