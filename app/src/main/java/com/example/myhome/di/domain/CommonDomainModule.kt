package com.example.myhome.di.domain

import com.example.myhome.common.repositories.ApartmentRepository
import com.example.myhome.common.repositories.TypeOfServiceRepository
import com.example.myhome.common.usecases.ApartmentListUseCase
import com.example.myhome.common.usecases.TypeOfServiceListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class CommonDomainModule {
    @Provides
    fun provideApartmentListUseCase(apartmentRepository: ApartmentRepository): ApartmentListUseCase {
        return ApartmentListUseCase(apartmentRepository = apartmentRepository)
    }
    @Provides
    fun provideTypeOfServiceListUseCase(typeOfServiceRepository: TypeOfServiceRepository): TypeOfServiceListUseCase {
        return TypeOfServiceListUseCase(typeOfServiceRepository = typeOfServiceRepository)
    }
}