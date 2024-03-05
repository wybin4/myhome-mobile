package com.example.myhome.event.usecases

import com.example.myhome.event.models.EventGetModel
import kotlinx.coroutines.flow.Flow

interface EventListUseCase {
    operator fun invoke(): Flow<EventGetModel>
}