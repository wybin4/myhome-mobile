package com.example.myhome.di

import com.example.myhome.features.common.CommonApiService
import com.example.myhome.features.common.mappers.SubscriberRemoteMapper
import com.example.myhome.features.common.repositories.SubscriberRepository
import com.example.myhome.features.common.repositories.SubscriberRepositoryImpl
import com.example.myhome.features.common.storages.SubscriberStorage
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
    fun provideSubscriberStorage(commonApiService: CommonApiService): SubscriberStorage {
        return SubscriberStorage(commonApiService)
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