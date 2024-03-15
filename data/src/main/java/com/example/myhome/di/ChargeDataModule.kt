package com.example.myhome.di

import com.example.myhome.features.charge.ChargeApiService
import com.example.myhome.features.charge.repositories.ChargeRepository
import com.example.myhome.features.charge.repositories.ChargeRepositoryImpl
import com.example.myhome.features.charge.ChargeStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ChargeDataModule {
    @Provides
    @Singleton
    fun provideChargeStorage(chargeApiService: ChargeApiService): ChargeStorage {
        return ChargeStorage(chargeApiService)
    }

    @Provides
    @Singleton
    fun provideChargeRepository(
        chargeStorage: ChargeStorage
    ): ChargeRepository {
        return ChargeRepositoryImpl(chargeStorage)
    }

}