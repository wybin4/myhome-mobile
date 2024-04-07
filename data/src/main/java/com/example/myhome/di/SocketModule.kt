package com.example.myhome.di

import com.example.myhome.features.CommonSocketService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SocketModule {

    @Provides
    @Singleton
    fun provideCommonSocketService(): CommonSocketService {
        return CommonSocketService()
    }

}