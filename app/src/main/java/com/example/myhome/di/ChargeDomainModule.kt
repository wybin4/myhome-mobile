package com.example.myhome.di

import com.example.myhome.features.charge.ChargeRepository
import com.example.myhome.features.charge.usecases.ChargeListUseCase
import com.example.myhome.features.charge.usecases.PaymentListUseCase
import com.example.myhome.features.charge.usecases.SinglePaymentDocumentGetUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class ChargeDomainModule {
    @Provides
    fun providePaymentListUseCase(chargeRepository: ChargeRepository): PaymentListUseCase {
        return PaymentListUseCase(chargeRepository = chargeRepository)
    }

    @Provides
    fun provideChargeListUseCase(chargeRepository: ChargeRepository): ChargeListUseCase {
        return ChargeListUseCase(chargeRepository = chargeRepository)
    }

    @Provides
    fun provideSinglePaymentDocumentGetUseCaseUseCase(
        chargeRepository: ChargeRepository
    ): SinglePaymentDocumentGetUseCase {
        return SinglePaymentDocumentGetUseCase(chargeRepository = chargeRepository)
    }
}