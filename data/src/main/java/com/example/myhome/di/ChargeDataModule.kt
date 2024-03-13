package com.example.myhome.di

import com.example.myhome.features.charge.ChargeApiService
import com.example.myhome.features.charge.ChargeRemoteMapper
import com.example.myhome.features.charge.ChargeRepository
import com.example.myhome.features.charge.ChargeRepositoryImpl
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
        chargeStorage: ChargeStorage, chargeRemoteMapper: ChargeRemoteMapper
    ): ChargeRepository {
        return ChargeRepositoryImpl(
            chargeStorage,
            chargeRemoteMapper
        )
    }

    @Provides
    @Singleton
    fun provideChargeMapper(): ChargeRemoteMapper {
        return ChargeRemoteMapper()
    }

}