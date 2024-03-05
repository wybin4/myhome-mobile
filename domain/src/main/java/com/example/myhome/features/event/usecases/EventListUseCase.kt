package com.example.myhome.features.event.usecases

import com.example.myhome.features.event.models.EventListModel
import kotlinx.coroutines.flow.Flow

interface EventListUseCase {
    operator fun invoke(): Flow<EventListModel>
}