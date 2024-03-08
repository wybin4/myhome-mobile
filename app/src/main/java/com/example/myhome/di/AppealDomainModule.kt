package com.example.myhome.di

import com.example.myhome.features.appeal.AppealRepository
import com.example.myhome.features.appeal.usecases.AppealAddUseCase
import com.example.myhome.features.appeal.usecases.AppealListUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class AppealDomainModule {
    @Provides
    fun provideAppealAddUseCase(appealRepository: AppealRepository): AppealAddUseCase {
        return AppealAddUseCase(appealRepository = appealRepository)
    }

    @Provides
    fun provideAppealListUseCase(appealRepository: AppealRepository): AppealListUseCase {
        return AppealListUseCase(appealRepository = appealRepository)
    }
}