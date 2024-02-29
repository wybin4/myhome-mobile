package com.example.myhome.di.domain

import com.example.myhome.meter.repositories.MeterRepository
import com.example.myhome.meter.repositories.ReadingRepository
import com.example.myhome.meter.usecases.ApartmentWithMeterListUseCase
import com.example.myhome.meter.usecases.ApartmentWithMeterListUseCaseImpl
import com.example.myhome.meter.usecases.MeterListUseCase
import com.example.myhome.meter.usecases.MeterListUseCaseImpl
import com.example.myhome.meter.usecases.ReadingAddUseCase
import com.example.myhome.meter.usecases.ReadingAddUseCaseImpl
import com.example.myhome.meter.usecases.ReadingListUseCase
import com.example.myhome.meter.usecases.ReadingListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

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
    fun provideReadingAddUseCase(readingRepository: ReadingRepository): ReadingAddUseCase {
        return ReadingAddUseCaseImpl(readingRepository = readingRepository)
    }

    @Provides
    fun provideReadingListUseCase(readingRepository: ReadingRepository): ReadingListUseCase {
        return ReadingListUseCaseImpl(readingRepository = readingRepository)
    }
}