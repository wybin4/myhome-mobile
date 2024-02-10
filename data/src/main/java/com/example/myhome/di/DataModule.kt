package com.example.myhome.di

import com.example.myhome.base.mappers.DateMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {
    @Provides
    @Singleton
    fun provideDateMapper(): DateMapper {
        return DateMapper()
    }
}