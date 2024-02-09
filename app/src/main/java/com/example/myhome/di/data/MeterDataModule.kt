package com.example.myhome.di.data

import com.example.myhome.data.base.mappers.DateMapper
import com.example.myhome.data.meter.mappers.MeterRemoteMapper
import com.example.myhome.data.meter.repositories.MeterRepositoryImpl
import com.example.myhome.data.meter.services.MeterApiService
import com.example.myhome.data.base.storages.MeterStorage
import com.example.myhome.data.meter.storages.MeterRemoteStorage
import com.example.myhome.domain.meter.repositories.MeterRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MeterDataModule {
    //    @Provides
//    @Singleton
//    fun provideReadingRepository(readingStorage: MeterStorage, readingRemoteMapper: ReadingRemoteMapper): ReadingRepository {
//        return ReadingRepositoryImpl(readingStorage, readingRemoteMapper)
//    }
//    @Provides
//    @Singleton
//    fun provideReadingMapper(dateMapper: DateMapper): ReadingRemoteMapper {
//        return ReadingRemoteMapper(dateMapper)
//    }
    @Provides
    @Singleton
    fun provideMeterStorage(meterApiService: MeterApiService): MeterStorage {
        return MeterRemoteStorage(meterApiService)
    }
    @Provides
    @Singleton
    fun provideMeterRepository(meterStorage: MeterStorage, meterRemoteMapper: MeterRemoteMapper): MeterRepository {
        return MeterRepositoryImpl(meterStorage, meterRemoteMapper)
    }
    @Provides
    @Singleton
    fun provideMeterMapper(dateMapper: DateMapper): MeterRemoteMapper {
        return MeterRemoteMapper(dateMapper)
    }

}