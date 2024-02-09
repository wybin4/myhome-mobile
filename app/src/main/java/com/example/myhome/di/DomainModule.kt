package com.example.myhome.di

import com.example.myhome.domain.repositories.MeterRepository
import com.example.myhome.domain.usecases.MeterAddUseCase
import com.example.myhome.domain.usecases.MeterListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {
    @Provides
    fun provideMeterAddUseCase(meterRepository: MeterRepository): MeterAddUseCase {
        return MeterAddUseCase(meterRepository = meterRepository)
    }
    @Provides
    fun provideMeterListUseCase(meterRepository: MeterRepository): MeterListUseCase {
        return MeterListUseCase(meterRepository = meterRepository)
    }
}