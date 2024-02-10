package com.example.myhome.di

import com.example.myhome.common.mappers.ApartmentRemoteMapper
import com.example.myhome.common.repositories.ApartmentRepository
import com.example.myhome.common.repositories.ApartmentRepositoryImpl
import com.example.myhome.common.services.ApartmentApiService
import com.example.myhome.common.storages.ApartmentRemoteStorage
import com.example.myhome.common.storages.ApartmentStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApartmentDataModule {
    @Provides
    @Singleton
    fun provideApartmentStorage(meterApiService: ApartmentApiService): ApartmentStorage {
        return ApartmentRemoteStorage(meterApiService)
    }
    @Provides
    @Singleton
    fun provideApartmentRepository(meterStorage: ApartmentStorage, meterRemoteMapper: ApartmentRemoteMapper): ApartmentRepository {
        return ApartmentRepositoryImpl(
            meterStorage,
            meterRemoteMapper
        )
    }
    @Provides
    @Singleton
    fun provideApartmentMapper(): ApartmentRemoteMapper {
        return ApartmentRemoteMapper()
    }

}