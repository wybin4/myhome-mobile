package com.example.myhome.di

import com.example.myhome.features.appeal.AppealApiService
import com.example.myhome.features.appeal.repositories.AppealRepositoryImpl
import com.example.myhome.features.appeal.AppealStorage
import com.example.myhome.features.appeal.repositories.AppealRepository
import com.example.myhome.features.event.EventApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppealDataModule {
    @Provides
    @Singleton
    fun provideAppealStorage(
        appealApiService: AppealApiService
    ): AppealStorage {
        return AppealStorage(appealApiService)
    }

    @Provides
    @Singleton
    fun provideAppealRepository(
        appealStorage: AppealStorage,
        eventApiService: EventApiService
    ): AppealRepository {
        return AppealRepositoryImpl(appealStorage, eventApiService)
    }

}