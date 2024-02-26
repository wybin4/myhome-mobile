package com.example.myhome.di.domain

import com.example.myhome.meter.usecases.ApartmentWithMeterListUseCase
import com.example.myhome.testutils.mocks.meter.usecases.ApartmentWithMeterListUseCaseTest
import com.example.myhome.meter.usecases.ReadingAddUseCase
import com.example.myhome.testutils.mocks.meter.usecases.ReadingAddUseCaseTest
import com.example.myhome.meter.usecases.ReadingListUseCase
import com.example.myhome.testutils.mocks.meter.usecases.ReadingListUseCaseTest
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [ViewModelComponent::class],
    replaces = [MeterDomainModule::class]
)
class FakeMeterDomainModule {
    @Provides
    fun provideApartmentWithMeterListUseCase(): ApartmentWithMeterListUseCase {
        return ApartmentWithMeterListUseCaseTest()
    }

    @Provides
    fun provideReadingAddUseCase(): ReadingAddUseCase {
        return ReadingAddUseCaseTest()
    }

    @Provides
    fun provideReadingListUseCase(): ReadingListUseCase {
        return ReadingListUseCaseTest()
    }
}