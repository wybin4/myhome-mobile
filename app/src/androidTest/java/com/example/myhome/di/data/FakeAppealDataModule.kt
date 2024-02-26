package com.example.myhome.di.data

import com.example.myhome.appeal.repositories.AppealRepository
import com.example.myhome.di.AppealDataModule
import com.example.myhome.testutils.mocks.appeal.repositories.AppealRepositoryTest
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppealDataModule::class]
)
class FakeAppealDataModule {

    @Provides
    @Singleton
    fun provideAppealRepository(): AppealRepository {
        return AppealRepositoryTest()
    }

}