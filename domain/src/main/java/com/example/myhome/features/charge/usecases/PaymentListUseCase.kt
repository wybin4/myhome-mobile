package com.example.myhome.features.charge.usecases

import com.example.myhome.features.charge.ChargeRepository
import com.example.myhome.features.charge.models.PaymentListItemModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PaymentListUseCase @Inject constructor(
    private val chargeRepository: ChargeRepository
) {
    operator fun invoke(spdId: Int): Flow<List<PaymentListItemModel>> {
        return chargeRepository.listPayment(spdId)
    }
}
