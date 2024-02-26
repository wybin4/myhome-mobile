package com.example.myhome.di.data

import com.example.myhome.common.repositories.ApartmentRepository
import com.example.myhome.di.ApartmentDataModule
import com.example.myhome.testutils.mocks.common.repositories.ApartmentRepositoryTest
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [ApartmentDataModule::class]
)
class FakeApartmentDataModule {
    @Provides
    @Singleton
    fun provideApartmentRepository(): ApartmentRepository {
        return ApartmentRepositoryTest()
    }
}