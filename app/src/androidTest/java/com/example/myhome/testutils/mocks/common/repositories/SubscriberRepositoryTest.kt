package com.example.myhome.testutils.mocks.common.repositories

import com.example.myhome.common.models.SubscriberGetModel
import com.example.myhome.common.repositories.SubscriberRepository
import com.example.myhome.testutils.providers.CommonDomainTestListProvider.getSubscriberList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SubscriberRepositoryTest : SubscriberRepository {
    override fun listSubscriber(): Flow<List<SubscriberGetModel>> {
        return flowOf(getSubscriberList())
    }
}
