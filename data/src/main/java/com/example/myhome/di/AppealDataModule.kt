package com.example.myhome.di

import com.example.myhome.appeal.services.AppealApiService
import com.example.myhome.appeal.storages.AppealRemoteStorage
import com.example.myhome.appeal.storages.AppealStorage
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
    fun provideAppealStorage(appealApiService: AppealApiService): AppealStorage {
        return AppealRemoteStorage(appealApiService)
    }
//    @Provides
//    @Singleton
//    fun provideAppealRepository(appealStorage: AppealStorage, appealRemoteMapper: AppealRemoteMapper): AppealRepository {
//        return AppealRepositoryImpl(
//            appealStorage,
//            appealRemoteMapper
//        )
//    }
//    @Provides
//    @Singleton
//    fun provideAppealMapper(): AppealRemoteMapper {
//        return AppealRemoteMapper()
//    }

}