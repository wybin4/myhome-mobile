package com.example.myhome.common.repositories

import com.example.myhome.common.mappers.SubscriberRemoteMapper
import com.example.myhome.common.models.SubscriberGetModel
import com.example.myhome.common.storages.SubscriberStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SubscriberRepositoryImpl(
    private val subscriberStorage: SubscriberStorage,
    private val subscriberRemoteMapper: SubscriberRemoteMapper
): SubscriberRepository {
    override fun listSubscriber(): Flow<List<SubscriberGetModel>> = flow {
        val result = subscriberStorage.listSubscriber()
        emit(subscriberRemoteMapper.mapListToDomain(result))
    }
}
