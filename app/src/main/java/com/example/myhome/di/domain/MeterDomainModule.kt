package com.example.myhome.di.domain

import com.example.myhome.meter.repositories.MeterRepository
import com.example.myhome.meter.usecases.MeterAddUseCase
import com.example.myhome.meter.usecases.MeterListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MeterDomainModule {
    @Provides
    fun provideMeterAddUseCase(meterRepository: MeterRepository): MeterAddUseCase {
        return MeterAddUseCase(meterRepository = meterRepository)
    }
    @Provides
    fun provideMeterListUseCase(meterRepository: MeterRepository): MeterListUseCase {
        return MeterListUseCase(meterRepository = meterRepository)
    }
}