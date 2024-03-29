package com.example.myhome.di

import androidx.paging.PagingConfig
import com.example.myhome.features.charge.ChargeApiService
import com.example.myhome.features.charge.ChargePagingSource
import com.example.myhome.features.charge.ChargeStorage
import com.example.myhome.features.charge.repositories.ChargeRepository
import com.example.myhome.features.charge.repositories.ChargeRepositoryImpl
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
    fun provideChargePagingSource(chargeApiService: ChargeApiService): ChargePagingSource {
        return ChargePagingSource(chargeApiService)
    }

    @Provides
    @Singleton
    fun provideChargeRepository(
        chargeStorage: ChargeStorage,
        chargePagingSource: ChargePagingSource
    ): ChargeRepository {
        return ChargeRepositoryImpl(
            chargeStorage, chargePagingSource,
            PagingConfig(pageSize = ChargePagingSource.CHARGE_PAGE_SIZE)
        )
    }

}