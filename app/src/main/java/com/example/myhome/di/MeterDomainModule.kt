package com.example.myhome.di

import com.example.myhome.features.meter.repositories.MeterRepository
import com.example.myhome.features.meter.repositories.ReadingRepository
import com.example.myhome.features.meter.usecases.ApartmentWithMeterListUseCase
import com.example.myhome.features.meter.usecases.ApartmentWithMeterListUseCaseImpl
import com.example.myhome.features.meter.usecases.MeterListUseCase
import com.example.myhome.features.meter.usecases.MeterListUseCaseImpl
import com.example.myhome.features.meter.usecases.ReadingAddUseCaseImpl
import com.example.myhome.features.meter.usecases.ReadingListUseCase
import com.example.myhome.features.meter.usecases.ReadingListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import com.example.myhome.features.meter.usecases.ReadingAddUseCase as ReadingAddUseCase1

@Module
@InstallIn(ViewModelComponent::class)
class MeterDomainModule {
    @Provides
    fun provideApartmentWithMeterListUseCase(meterRepository: MeterRepository): ApartmentWithMeterListUseCase {
        return ApartmentWithMeterListUseCaseImpl(meterRepository = meterRepository)
    }

    @Provides
    fun provideMeterListUseCase(meterRepository: MeterRepository): MeterListUseCase {
        return MeterListUseCaseImpl(meterRepository = meterRepository)
    }

    @Provides
    fun provideReadingAddUseCase(readingRepository: ReadingRepository): ReadingAddUseCase1 {
        return ReadingAddUseCaseImpl(readingRepository = readingRepository)
    }

    @Provides
    fun provideReadingListUseCase(readingRepository: ReadingRepository): ReadingListUseCase {
        return ReadingListUseCaseImpl(readingRepository = readingRepository)
    }
}