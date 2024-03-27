package com.example.myhome.di

import androidx.paging.PagingConfig
import com.example.myhome.features.event.EventApiService
import com.example.myhome.features.event.EventPagingSource
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
    fun provideEventRepository(
        eventApiService: EventApiService,
        eventPagingSource: EventPagingSource
    ): EventRepository {
        return EventRepositoryImpl(
            eventApiService,
            eventPagingSource,
            PagingConfig(pageSize = EventPagingSource.EVENT_PAGE_SIZE)
        )
    }

    @Provides
    @Singleton
    fun provideEventPagingSource(eventApiService: EventApiService): EventPagingSource {
        return EventPagingSource(eventApiService)
    }
}