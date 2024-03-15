package com.example.myhome.testutils.mocks.common.repositories

import com.example.myhome.features.common.dtos.SubscriberListItemResponse
import com.example.myhome.features.common.repositories.SubscriberRepository
import com.example.myhome.testutils.CommonDomainTestListProvider.getSubscriberList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SubscriberRepositoryTest : SubscriberRepository {
    override fun listSubscriber(): Flow<List<SubscriberListItemResponse>> {
        return flowOf(getSubscriberList())
    }
}
