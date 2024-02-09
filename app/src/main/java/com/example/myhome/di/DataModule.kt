package com.example.myhome.di

import android.content.Context
import com.example.myhome.data.mappers.DateMapper
import com.example.myhome.data.mappers.remote.MeterRemoteMapper
import com.example.myhome.data.mappers.remote.ReadingRemoteMapper
import com.example.myhome.data.repositories.MeterRepositoryImpl
import com.example.myhome.data.repositories.ReadingRepositoryImpl
import com.example.myhome.data.services.MeterApiService
import com.example.myhome.data.storages.MeterStorage
import com.example.myhome.data.storages.remote.MeterRemoteStorage
import com.example.myhome.domain.repositories.MeterRepository
import com.example.myhome.domain.repositories.ReadingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
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
    @Provides
    @Singleton
    fun provideDateMapper(): DateMapper {
        return DateMapper()
    }
}