package com.example.myhome.di

import com.example.myhome.features.event.EventApiService
import com.example.myhome.features.event.repositories.EventRepository
import com.example.myhome.features.event.repositories.EventRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class EventDataModule {
    @Provides
    @Singleton
    fun provideEventRepository(eventApiService: EventApiService): EventRepository {
        return EventRepositoryImpl(eventApiService)
    }
}