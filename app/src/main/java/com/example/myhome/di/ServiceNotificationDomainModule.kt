package com.example.myhome.di

import com.example.myhome.features.servicenotification.ServiceNotificationListUseCase
import com.example.myhome.features.servicenotification.ServiceNotificationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal class ServiceNotificationDomainModule {
    @Provides
    fun provideServiceNotificationListUseCase(
        notificationRepository: ServiceNotificationRepository
    ): ServiceNotificationListUseCase {
        return ServiceNotificationListUseCase(notificationRepository = notificationRepository)
    }

}