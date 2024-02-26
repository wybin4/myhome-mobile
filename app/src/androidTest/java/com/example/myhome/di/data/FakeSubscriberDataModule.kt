package com.example.myhome.di.data

import com.example.myhome.common.repositories.SubscriberRepository
import com.example.myhome.di.SubscriberDataModule
import com.example.myhome.testutils.mocks.common.repositories.SubscriberRepositoryTest
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [SubscriberDataModule::class]
)
class FakeSubscriberDataModule {
    @Provides
    @Singleton
    fun provideSubscriberRepository(): SubscriberRepository {
        return SubscriberRepositoryTest()
    }
}