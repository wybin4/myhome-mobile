package com.example.myhome.testutils.mocks.common.usecases

import com.example.myhome.common.models.SubscriberGetModel
import com.example.myhome.common.usecases.SubscriberListUseCase
import com.example.myhome.testutils.providers.CommonDomainTestListProvider.getSubscriberList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SubscriberListUseCaseTest : SubscriberListUseCase {
    override operator fun invoke(): Flow<List<SubscriberGetModel>> {
        return flowOf(getSubscriberList())
    }
}
