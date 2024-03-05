package com.example.myhome.features.event.usecases

import com.example.myhome.features.event.EventRepository
import com.example.myhome.features.event.models.EventListModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EventListUseCaseImpl @Inject constructor(
    private val eventRepository: EventRepository
) : EventListUseCase {
    override operator fun invoke(): Flow<EventListModel> {
        return eventRepository.listEvent()
    }

}