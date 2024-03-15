package com.example.myhome.di

import com.example.myhome.features.SocketService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SocketModule {

    @Provides
    @Singleton
    fun provideSocketService(): SocketService {
        return SocketService()
    }

}