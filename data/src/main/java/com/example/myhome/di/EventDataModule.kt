package com.example.myhome.di

import androidx.paging.PagingConfig
import com.example.myhome.features.event.EventApiService
import com.example.myhome.features.event.pagingsources.EventPagingSource
import com.example.myhome.features.event.pagingsources.EventPagingSourceFactory
import com.example.myhome.features.event.repositories.EventRepository
import com.example.myhome.features.event.repositories.EventRepositoryImpl
import com.example.myhome.features.servicenotifications.pagingsources.ServiceNotificationPagingSource.Companion.NOTIFICATION_PAGE_SIZE
import com.example.myhome.features.servicenotifications.pagingsources.ServiceNotificationPagingSourceFactory
import com.example.myhome.features.servicenotifications.repositories.ServiceNotificationRepository
import com.example.myhome.features.servicenotifications.repositories.ServiceNotificationRepositoryImpl
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
        eventPagingSourceFactory: EventPagingSourceFactory
    ): EventRepository {
        return EventRepositoryImpl(
            eventApiService,
            eventPagingSourceFactory,
            PagingConfig(pageSize = EventPagingSource.EVENT_PAGE_SIZE)
        )
    }

    @Provides
    @Singleton
    fun provideServiceNotificationRepository(
        eventApiService: EventApiService,
        serviceNotificationPagingSourceFactory: ServiceNotificationPagingSourceFactory
    ): ServiceNotificationRepository {
        return ServiceNotificationRepositoryImpl(
            eventApiService,
            serviceNotificationPagingSourceFactory,
            PagingConfig(pageSize = NOTIFICATION_PAGE_SIZE)
        )
    }

    @Provides
    @Singleton
    fun provideEventPagingSourceFactory(eventApiService: EventApiService): EventPagingSourceFactory {
        return EventPagingSourceFactory(eventApiService)
    }

    @Provides
    @Singleton
    fun provideServiceNotificationPagingSourceFactory(
        eventApiService: EventApiService
    ): ServiceNotificationPagingSourceFactory {
        return ServiceNotificationPagingSourceFactory(eventApiService)
    }

}