package com.example.myhome.common.usecases

import com.example.myhome.common.models.SubscriberGetModel
import kotlinx.coroutines.flow.Flow

interface SubscriberListUseCase {
    operator fun invoke(): Flow<List<SubscriberGetModel>>
}
