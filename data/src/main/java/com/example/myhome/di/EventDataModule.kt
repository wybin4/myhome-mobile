package com.example.myhome.di

import com.example.myhome.features.event.EventApiService
import com.example.myhome.features.event.EventRemoteMapper
import com.example.myhome.features.event.EventRepository
import com.example.myhome.features.event.EventRepositoryImpl
import com.example.myhome.features.event.EventStorage
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
        return EventStorage(eventApiService)
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