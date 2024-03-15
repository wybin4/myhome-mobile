package com.example.myhome.di

import com.example.myhome.features.meter.MeterApiService
import com.example.myhome.features.meter.repositories.MeterRepository
import com.example.myhome.features.meter.repositories.MeterRepositoryImpl
import com.example.myhome.features.meter.repositories.ReadingRepository
import com.example.myhome.features.meter.repositories.ReadingRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MeterDataModule {
    @Provides
    @Singleton
    fun provideReadingRepository(meterApiService: MeterApiService): ReadingRepository {
        return ReadingRepositoryImpl(meterApiService)
    }

    @Provides
    @Singleton
    fun provideMeterRepository(meterApiService: MeterApiService): MeterRepository {
        return MeterRepositoryImpl(meterApiService)
    }

}