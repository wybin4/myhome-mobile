package com.example.myhome.di.data

import com.example.myhome.di.CommonDataModule
import com.example.myhome.features.common.repositories.ApartmentRepository
import com.example.myhome.features.common.repositories.SubscriberRepository
import com.example.myhome.features.common.repositories.TypeOfServiceRepository
import com.example.myhome.testutils.mocks.common.repositories.ApartmentRepositoryTest
import com.example.myhome.testutils.mocks.common.repositories.SubscriberRepositoryTest
import com.example.myhome.testutils.mocks.common.repositories.TypeOfServiceRepositoryTest
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CommonDataModule::class]
)
class FakeCommonDataModule {
    @Provides
    @Singleton
    fun provideApartmentRepository(): ApartmentRepository {
        return ApartmentRepositoryTest()
    }

    @Provides
    @Singleton
    fun provideSubscriberRepository(): SubscriberRepository {
        return SubscriberRepositoryTest()
    }

    @Provides
    @Singleton
    fun provideTypeOfServiceRepository(): TypeOfServiceRepository {
        return TypeOfServiceRepositoryTest()
    }
}