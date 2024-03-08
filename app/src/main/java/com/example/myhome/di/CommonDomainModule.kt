package com.example.myhome.di

import com.example.myhome.features.common.repositories.ApartmentRepository
import com.example.myhome.features.common.repositories.SubscriberRepository
import com.example.myhome.features.common.repositories.TypeOfServiceRepository
import com.example.myhome.features.common.usecases.ApartmentListUseCase
import com.example.myhome.features.common.usecases.SubscriberListUseCase
import com.example.myhome.features.common.usecases.TypeOfServiceListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
internal class CommonDomainModule {
    @Provides
    fun provideApartmentListUseCase(apartmentRepository: ApartmentRepository): ApartmentListUseCase {
        return ApartmentListUseCase(apartmentRepository = apartmentRepository)
    }

    @Provides
    fun provideTypeOfServiceListUseCase(typeOfServiceRepository: TypeOfServiceRepository): TypeOfServiceListUseCase {
        return TypeOfServiceListUseCase(typeOfServiceRepository = typeOfServiceRepository)
    }

    @Provides
    fun provideSubscriberListUseCase(subscriberRepository: SubscriberRepository): SubscriberListUseCase {
        return SubscriberListUseCase(subscriberRepository = subscriberRepository)
    }
}