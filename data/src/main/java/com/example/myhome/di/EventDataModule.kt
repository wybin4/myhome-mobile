package com.example.myhome.di

import com.example.myhome.event.mappers.EventRemoteMapper
import com.example.myhome.event.repositories.EventRepository
import com.example.myhome.event.repositories.EventRepositoryImpl
import com.example.myhome.event.storages.EventRemoteStorage
import com.example.myhome.event.storages.EventStorage
import com.example.myhome.event.services.EventApiService
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
    fun provideEventStorage(eventApiService: EventApiService): EventStorage {
        return EventRemoteStorage(eventApiService)
    }

    @Provides
    @Singleton
    fun provideEventRepository(eventStorage: EventStorage, eventRemoteMapper: EventRemoteMapper): EventRepository {
        return EventRepositoryImpl(
            eventStorage,
            eventRemoteMapper
        )
    }

    @Provides
    @Singleton
    fun provideEventMapper(): EventRemoteMapper {
        return EventRemoteMapper()
    }

}