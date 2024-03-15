package com.example.myhome.di

import com.example.myhome.features.common.CommonApiService
import com.example.myhome.features.common.repositories.ApartmentRepository
import com.example.myhome.features.common.repositories.ApartmentRepositoryImpl
import com.example.myhome.features.common.repositories.SubscriberRepository
import com.example.myhome.features.common.repositories.SubscriberRepositoryImpl
import com.example.myhome.features.common.repositories.TypeOfServiceRepository
import com.example.myhome.features.common.repositories.TypeOfServiceRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommonDataModule {
    @Provides
    @Singleton
    fun provideSubscriberRepository(commonApiService: CommonApiService): SubscriberRepository {
        return SubscriberRepositoryImpl(commonApiService)
    }

    @Provides
    @Singleton
    fun provideApartmentRepository(commonApiService: CommonApiService): ApartmentRepository {
        return ApartmentRepositoryImpl(commonApiService)
    }

    @Provides
    @Singleton
    fun provideTypeOfServiceRepository(commonApiService: CommonApiService): TypeOfServiceRepository {
        return TypeOfServiceRepositoryImpl(commonApiService)
    }
}