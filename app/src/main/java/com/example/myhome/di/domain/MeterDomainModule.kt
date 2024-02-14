package com.example.myhome.di.domain

import com.example.myhome.meter.repositories.MeterRepository
import com.example.myhome.meter.usecases.ApartmentWithMeterListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class MeterDomainModule {
    @Provides
    fun provideApartmentWithMeterListUseCase(meterRepository: MeterRepository): ApartmentWithMeterListUseCase {
        return ApartmentWithMeterListUseCase(meterRepository = meterRepository)
    }
}