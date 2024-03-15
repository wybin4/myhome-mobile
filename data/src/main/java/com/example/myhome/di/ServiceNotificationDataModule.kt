package com.example.myhome.di

import com.example.myhome.features.SocketService
import com.example.myhome.features.servicenotification.repositories.ServiceNotificationRepository
import com.example.myhome.features.servicenotification.repositories.ServiceNotificationRepositoryImpl
import com.example.myhome.features.servicenotification.ServiceNotificationStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ServiceNotificationDataModule {
    @Provides
    @Singleton
    fun provideServiceNotificationStorage(socketService: SocketService): ServiceNotificationStorage {
        return ServiceNotificationStorage(socketService)
    }

    @Provides
    @Singleton
    fun provideServiceNotificationRepository(subscriberStorage: ServiceNotificationStorage): ServiceNotificationRepository {
        return ServiceNotificationRepositoryImpl(subscriberStorage)
    }

}