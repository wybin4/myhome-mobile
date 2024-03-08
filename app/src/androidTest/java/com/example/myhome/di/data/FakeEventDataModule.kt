package com.example.myhome.di.data

import com.example.myhome.di.EventDataModule
import com.example.myhome.di.MeterDataModule
import com.example.myhome.features.event.EventRepository
import com.example.myhome.testutils.mocks.event.EventRepositoryTest
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [EventDataModule::class]
)
class FakeEventDataModule {
    @Provides
    @Singleton
    fun provideEventRepository(): EventRepository {
        return EventRepositoryTest()
    }
}