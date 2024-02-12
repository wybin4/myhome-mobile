package com.example.myhome.di

import com.example.myhome.common.mappers.SubscriberRemoteMapper
import com.example.myhome.common.repositories.SubscriberRepository
import com.example.myhome.common.repositories.SubscriberRepositoryImpl
import com.example.myhome.common.services.SubscriberApiService
import com.example.myhome.common.storages.SubscriberRemoteStorage
import com.example.myhome.common.storages.SubscriberStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SubscriberDataModule {
    @Provides
    @Singleton
    fun provideSubscriberStorage(subscriberApiService: SubscriberApiService): SubscriberStorage {
        return SubscriberRemoteStorage(subscriberApiService)
    }
    @Provides
    @Singleton
    fun provideSubscriberRepository(subscriberStorage: SubscriberStorage, subscriberRemoteMapper: SubscriberRemoteMapper): SubscriberRepository {
        return SubscriberRepositoryImpl(
            subscriberStorage,
            subscriberRemoteMapper
        )
    }
    @Provides
    @Singleton
    fun provideSubscriberMapper(): SubscriberRemoteMapper {
        return SubscriberRemoteMapper()
    }

}