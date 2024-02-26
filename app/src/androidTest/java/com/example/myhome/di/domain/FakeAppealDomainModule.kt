package com.example.myhome.di.domain

import com.example.myhome.appeal.usecases.AppealAddUseCase
import com.example.myhome.testutils.mocks.appeal.usecases.AppealAddUseCaseTest
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [ViewModelComponent::class],
    replaces = [AppealDomainModule::class]
)
class FakeAppealDomainModule {
    @Provides
    fun provideAppealAddUseCase(): AppealAddUseCase {
        return AppealAddUseCaseTest()
    }
}