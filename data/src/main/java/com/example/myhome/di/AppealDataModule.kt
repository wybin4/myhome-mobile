package com.example.myhome.di

import androidx.paging.PagingConfig
import com.example.myhome.features.appeal.AppealApiService
import com.example.myhome.features.appeal.AppealPagingSource
import com.example.myhome.features.appeal.AppealPagingSourceFactory
import com.example.myhome.features.appeal.repositories.AppealRepository
import com.example.myhome.features.appeal.repositories.AppealRepositoryImpl
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
    fun provideAppealRepository(
        appealApiService: AppealApiService,
        appealPagingSourceFactory: AppealPagingSourceFactory
    ): AppealRepository {
        return AppealRepositoryImpl(
            appealApiService,
            appealPagingSourceFactory,
            PagingConfig(pageSize = AppealPagingSource.APPEAL_PAGE_SIZE)
        )
    }

    @Provides
    @Singleton
    fun provideAppealPagingSourceFactory(eventApiService: EventApiService): AppealPagingSourceFactory {
        return AppealPagingSourceFactory(eventApiService)
    }

}