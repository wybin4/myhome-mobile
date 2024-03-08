package com.example.myhome.features.common.usecases

import com.example.myhome.features.common.models.SubscriberListItemModel
import com.example.myhome.features.common.repositories.SubscriberRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscriberListUseCase @Inject constructor(
    private val subscriberRepository: SubscriberRepository
) {
    operator fun invoke(): Flow<List<SubscriberListItemModel>> {
        return subscriberRepository.listSubscriber()
    }
}
