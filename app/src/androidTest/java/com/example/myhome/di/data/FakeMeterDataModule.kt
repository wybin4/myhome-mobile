package com.example.myhome.di.data

import com.example.myhome.di.MeterDataModule
import com.example.myhome.di.domain.MeterDomainModule
import com.example.myhome.meter.repositories.MeterRepository
import com.example.myhome.meter.repositories.ReadingRepository
import com.example.myhome.testutils.mocks.meter.repositories.MeterRepositoryTest
import com.example.myhome.testutils.mocks.meter.repositories.ReadingRepositoryTest
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [MeterDataModule::class]
)
class FakeMeterDataModule {
    @Provides
    @Singleton
    fun provideReadingRepository(): ReadingRepository {
        return ReadingRepositoryTest()
    }

    @Provides
    @Singleton
    fun provideMeterRepository(): MeterRepository {
        return MeterRepositoryTest()
    }
}