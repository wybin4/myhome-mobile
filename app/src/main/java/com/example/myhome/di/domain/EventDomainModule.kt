package com.example.myhome.di.domain

import com.example.myhome.event.repositories.EventRepository
import com.example.myhome.event.usecases.EventListUseCase
import com.example.myhome.event.usecases.EventListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class EventDomainModule {
    @Provides
    fun provideEventListUseCase(eventRepository: EventRepository): EventListUseCase {
        return EventListUseCaseImpl(eventRepository = eventRepository)
    }

}