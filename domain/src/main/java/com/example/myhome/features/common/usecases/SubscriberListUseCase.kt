package com.example.myhome.features.common.usecases

import com.example.myhome.features.common.models.SubscriberListItemModel
import kotlinx.coroutines.flow.Flow

interface SubscriberListUseCase {
    operator fun invoke(): Flow<List<SubscriberListItemModel>>
}
