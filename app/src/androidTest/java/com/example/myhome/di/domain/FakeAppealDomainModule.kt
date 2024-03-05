package com.example.myhome.di.domain

import com.example.myhome.di.AppealDomainModule
import com.example.myhome.features.appeal.usecases.AppealAddUseCase
import com.example.myhome.features.appeal.usecases.AppealListUseCase
import com.example.myhome.testutils.mocks.appeal.usecases.AppealAddUseCaseTest
import com.example.myhome.testutils.mocks.appeal.usecases.AppealListUseCaseTest
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

    @Provides
    fun provideAppealListUseCase(): AppealListUseCase {
        return AppealListUseCaseTest()
    }
}