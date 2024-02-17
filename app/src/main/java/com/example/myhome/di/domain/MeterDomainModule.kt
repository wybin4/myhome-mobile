package com.example.myhome.di.domain

import com.example.myhome.meter.repositories.MeterRepository
import com.example.myhome.meter.repositories.ReadingRepository
import com.example.myhome.meter.usecases.ApartmentWithMeterListUseCase
import com.example.myhome.meter.usecases.ReadingAddUseCase
import com.example.myhome.meter.usecases.ReadingListUseCase
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
    @Provides
    fun provideReadingAddUseCase(readingRepository: ReadingRepository): ReadingAddUseCase {
        return ReadingAddUseCase(readingRepository = readingRepository)
    }
    @Provides
    fun provideReadingListUseCase(readingRepository: ReadingRepository): ReadingListUseCase {
        return ReadingListUseCase(readingRepository = readingRepository)
    }
}