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
    fun provideApartmentStorage(apartmentApiService: ApartmentApiService): ApartmentStorage {
        return ApartmentRemoteStorage(apartmentApiService)
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