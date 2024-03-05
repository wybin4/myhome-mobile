package com.example.myhome.di

import com.example.myhome.features.common.mappers.ApartmentRemoteMapper
import com.example.myhome.features.common.repositories.ApartmentRepositoryImpl
import com.example.myhome.features.common.CommonApiService
import com.example.myhome.features.common.repositories.ApartmentRepository
import com.example.myhome.features.common.storages.ApartmentStorage
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
    fun provideApartmentStorage(commonApiService: CommonApiService): ApartmentStorage {
        return ApartmentStorage(commonApiService)
    }

    @Provides
    @Singleton
    fun provideApartmentRepository(apartmentStorage: ApartmentStorage, apartmentRemoteMapper: ApartmentRemoteMapper): ApartmentRepository {
        return ApartmentRepositoryImpl(
            apartmentStorage,
            apartmentRemoteMapper
        )
    }

    @Provides
    @Singleton
    fun provideApartmentMapper(): ApartmentRemoteMapper {
        return ApartmentRemoteMapper()
    }

}