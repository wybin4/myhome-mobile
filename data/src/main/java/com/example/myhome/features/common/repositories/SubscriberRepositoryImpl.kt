package com.example.myhome.features.common.repositories

import com.example.myhome.features.common.mappers.SubscriberRemoteMapper
import com.example.myhome.features.common.models.SubscriberListItemModel
import com.example.myhome.features.common.storages.SubscriberStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SubscriberRepositoryImpl(
    private val subscriberStorage: SubscriberStorage,
    private val subscriberRemoteMapper: SubscriberRemoteMapper
): SubscriberRepository {
    override fun listSubscriber(): Flow<List<SubscriberListItemModel>> = flow {
        val result = subscriberStorage.listSubscriber()
        emit(subscriberRemoteMapper.mapListToDomain(result))
    }
}
