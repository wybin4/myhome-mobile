package com.example.myhome.testutils.mocks.common.usecases

import com.example.myhome.features.common.models.SubscriberListItemModel
import com.example.myhome.features.common.usecases.SubscriberListUseCase
import com.example.myhome.testutils.providers.CommonDomainTestListProvider.getSubscriberList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SubscriberListUseCaseTest : SubscriberListUseCase {
    override operator fun invoke(): Flow<List<SubscriberListItemModel>> {
        return flowOf(getSubscriberList())
    }
}
