package com.example.myhome.di

import com.example.myhome.features.event.EventRepository
import com.example.myhome.features.event.usecases.EventListUseCase
import com.example.myhome.features.event.usecases.EventListUseCaseImpl
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