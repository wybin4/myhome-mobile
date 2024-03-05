package com.example.myhome.di.domain

import com.example.myhome.di.CommonDomainModule
import com.example.myhome.features.common.usecases.ApartmentListUseCase
import com.example.myhome.features.common.usecases.SubscriberListUseCase
import com.example.myhome.features.common.usecases.TypeOfServiceListUseCase
import com.example.myhome.testutils.mocks.common.usecases.ApartmentListUseCaseTest
import com.example.myhome.testutils.mocks.common.usecases.SubscriberListUseCaseTest
import com.example.myhome.testutils.mocks.common.usecases.TypeOfServiceListUseCaseTest
import dagger.Module
import dagger.Provides
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [ViewModelComponent::class],
    replaces = [CommonDomainModule::class]
)
class FakeCommonDomainModule {
    @Provides
    fun provideApartmentListUseCase(): ApartmentListUseCase {
        return ApartmentListUseCaseTest()
    }

    @Provides
    fun provideSubscriberListUseCase(): SubscriberListUseCase {
        return SubscriberListUseCaseTest()
    }

    @Provides
    fun provideTypeOfServiceListUseCase(): TypeOfServiceListUseCase {
        return TypeOfServiceListUseCaseTest()
    }

}