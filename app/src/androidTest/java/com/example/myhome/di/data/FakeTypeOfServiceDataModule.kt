package com.example.myhome.di.data

import com.example.myhome.di.TypeOfServiceDataModule
import com.example.myhome.features.common.repositories.TypeOfServiceRepository
import com.example.myhome.testutils.mocks.common.repositories.TypeOfServiceRepositoryTest
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [TypeOfServiceDataModule::class]
)
class FakeTypeOfServiceDataModule {
    @Provides
    @Singleton
    fun provideTypeOfServiceRepository(): TypeOfServiceRepository {
        return TypeOfServiceRepositoryTest()
    }
}