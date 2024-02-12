package com.example.myhome.common.usecases

import com.example.myhome.common.models.SubscriberGetModel
import com.example.myhome.common.repositories.SubscriberRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SubscriberListUseCase @Inject constructor(
    private val subscriberRepository: SubscriberRepository
) {
    operator fun invoke(): Flow<List<SubscriberGetModel>> {
        return subscriberRepository.listSubscriber()
    }
}
