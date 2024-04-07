package com.example.myhome.features.common.repositories

import com.example.myhome.features.common.CommonApiService
import com.example.myhome.features.common.dtos.SubscriberListItemResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SubscriberRepositoryImpl(
    private val commonApiService: CommonApiService
): SubscriberRepository {
    override fun listSubscriber(): Flow<List<SubscriberListItemResponse>> = flow {
        val response = commonApiService.listSubscriber()
        emit(response.users)
    }
}
