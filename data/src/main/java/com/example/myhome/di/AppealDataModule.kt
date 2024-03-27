package com.example.myhome.di

import androidx.paging.PagingConfig
import com.example.myhome.features.appeal.AppealApiService
import com.example.myhome.features.appeal.AppealPagingSource
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
        appealPagingSource: AppealPagingSource
    ): AppealRepository {
        return AppealRepositoryImpl(
            appealStorage,
            appealPagingSource,
            PagingConfig(pageSize = AppealPagingSource.APPEAL_PAGE_SIZE)
        )
    }

    @Provides
    @Singleton
    fun provideAppealPagingSource(eventApiService: EventApiService): AppealPagingSource {
        return AppealPagingSource(eventApiService)
    }

}