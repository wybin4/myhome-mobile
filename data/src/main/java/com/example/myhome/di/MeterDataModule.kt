package com.example.myhome.di

import com.example.myhome.features.meter.MeterApiService
import com.example.myhome.features.meter.MeterStorage
import com.example.myhome.features.meter.mappers.MeterRemoteMapper
import com.example.myhome.features.meter.mappers.ReadingRemoteMapper
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
    fun provideReadingRepository(meterStorage: MeterStorage, readingRemoteMapper: ReadingRemoteMapper): ReadingRepository {
        return ReadingRepositoryImpl(meterStorage, readingRemoteMapper)
    }
    @Provides
    @Singleton
    fun provideReadingMapper(): ReadingRemoteMapper {
        return ReadingRemoteMapper()
    }
    @Provides
    @Singleton
    fun provideMeterStorage(meterApiService: MeterApiService): MeterStorage {
        return MeterStorage(meterApiService)
    }
    @Provides
    @Singleton
    fun provideMeterRepository(
        meterStorage: MeterStorage,
        meterRemoteMapper: MeterRemoteMapper
    ): MeterRepository {
        return MeterRepositoryImpl(
            meterStorage, meterRemoteMapper
        )
    }
    @Provides
    @Singleton
    fun provideMeterMapper(): MeterRemoteMapper {
        return MeterRemoteMapper()
    }

}