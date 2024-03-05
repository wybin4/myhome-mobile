package com.example.myhome.event.usecases

import com.example.myhome.event.models.EventGetModel
import com.example.myhome.event.repositories.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventListUseCaseImpl @Inject constructor(
    private val eventRepository: EventRepository
) : EventListUseCase {
    override operator fun invoke(): Flow<EventGetModel> {
        return eventRepository.listEvent()
    }

}