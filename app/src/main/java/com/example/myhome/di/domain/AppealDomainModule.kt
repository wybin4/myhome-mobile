package com.example.myhome.di.domain

import com.example.myhome.appeal.repositories.AppealRepository
import com.example.myhome.appeal.usecases.AppealAddUseCase
import com.example.myhome.appeal.usecases.AppealAddUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class AppealDomainModule {
    @Provides
    fun provideAppealAddUseCase(appealRepository: AppealRepository): AppealAddUseCase {
        return AppealAddUseCaseImpl(appealRepository = appealRepository)
    }
}