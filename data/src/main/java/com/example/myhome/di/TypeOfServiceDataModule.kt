package com.example.myhome.di

import com.example.myhome.features.common.CommonApiService
import com.example.myhome.features.common.mappers.TypeOfServiceRemoteMapper
import com.example.myhome.features.common.repositories.TypeOfServiceRepository
import com.example.myhome.features.common.repositories.TypeOfServiceRepositoryImpl
import com.example.myhome.features.common.storages.TypeOfServiceStorage
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
    fun provideTypeOfServiceStorage(commonApiService: CommonApiService): TypeOfServiceStorage {
        return TypeOfServiceStorage(commonApiService)
    }
    @Provides
    @Singleton
    fun provideTypeOfServiceRepository(
        typeOfServiceStorage: TypeOfServiceStorage,
        typeOfServiceRemoteMapper: TypeOfServiceRemoteMapper
    ): TypeOfServiceRepository {
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