package com.example.myhome.di

import com.example.myhome.common.mappers.TypeOfServiceRemoteMapper
import com.example.myhome.common.repositories.TypeOfServiceRepository
import com.example.myhome.common.repositories.TypeOfServiceRepositoryImpl
import com.example.myhome.common.services.TypeOfServiceApiService
import com.example.myhome.common.storages.TypeOfServiceRemoteStorage
import com.example.myhome.common.storages.TypeOfServiceStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TypeOfServiceDataModule {
    @Provides
    @Singleton
    fun provideTypeOfServiceStorage(typeOfServiceApiService: TypeOfServiceApiService): TypeOfServiceStorage {
        return TypeOfServiceRemoteStorage(typeOfServiceApiService)
    }
    @Provides
    @Singleton
    fun provideTypeOfServiceRepository(typeOfServiceStorage: TypeOfServiceStorage, typeOfServiceRemoteMapper: TypeOfServiceRemoteMapper): TypeOfServiceRepository {
        return TypeOfServiceRepositoryImpl(
            typeOfServiceStorage,
            typeOfServiceRemoteMapper
        )
    }
    @Provides
    @Singleton
    fun provideTypeOfServiceMapper(): TypeOfServiceRemoteMapper {
        return TypeOfServiceRemoteMapper()
    }

}