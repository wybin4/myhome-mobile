package com.example.myhome.di.domain

import com.example.myhome.common.repositories.ApartmentRepository
import com.example.myhome.common.repositories.SubscriberRepository
import com.example.myhome.common.repositories.TypeOfServiceRepository
import com.example.myhome.common.usecases.ApartmentListUseCase
import com.example.myhome.common.usecases.ApartmentListUseCaseImpl
import com.example.myhome.common.usecases.SubscriberListUseCase
import com.example.myhome.common.usecases.SubscriberListUseCaseImpl
import com.example.myhome.common.usecases.TypeOfServiceListUseCase
import com.example.myhome.common.usecases.TypeOfServiceListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class CommonDomainModule {
    @Provides
    fun provideApartmentListUseCase(apartmentRepository: ApartmentRepository): ApartmentListUseCase {
        return ApartmentListUseCaseImpl(apartmentRepository = apartmentRepository)
    }

    @Provides
    fun provideTypeOfServiceListUseCase(typeOfServiceRepository: TypeOfServiceRepository): TypeOfServiceListUseCase {
        return TypeOfServiceListUseCaseImpl(typeOfServiceRepository = typeOfServiceRepository)
    }

    @Provides
    fun provideSubscriberListUseCase(subscriberRepository: SubscriberRepository): SubscriberListUseCase {
        return SubscriberListUseCaseImpl(subscriberRepository = subscriberRepository)
    }
}