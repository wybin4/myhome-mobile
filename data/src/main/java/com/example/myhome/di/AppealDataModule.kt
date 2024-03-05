package com.example.myhome.di

import com.example.myhome.features.appeal.AppealApiService
import com.example.myhome.features.appeal.AppealRemoteMapper
import com.example.myhome.features.appeal.AppealRepository
import com.example.myhome.features.appeal.AppealRepositoryImpl
import com.example.myhome.features.appeal.AppealStorage
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
        appealApiService: AppealApiService,
        eventApiService: EventApiService
    ): AppealStorage {
        return AppealStorage(appealApiService, eventApiService)
    }

    @Provides
    @Singleton
    fun provideAppealRepository(appealStorage: AppealStorage, appealRemoteMapper: AppealRemoteMapper): AppealRepository {
        return AppealRepositoryImpl(
            appealStorage,
            appealRemoteMapper
        )
    }

    @Provides
    @Singleton
    fun provideAppealMapper(): AppealRemoteMapper {
        return AppealRemoteMapper()
    }

}