package com.example.myhome.features.common.usecases

import com.example.myhome.features.common.models.SubscriberListItemModel
import com.example.myhome.features.common.repositories.SubscriberRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscriberListUseCaseImpl @Inject constructor(
    private val subscriberRepository: SubscriberRepository
): SubscriberListUseCase {
    override operator fun invoke(): Flow<List<SubscriberListItemModel>> {
        return subscriberRepository.listSubscriber()
    }
}
