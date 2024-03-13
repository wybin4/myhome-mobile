package com.example.myhome.features.charge.usecases

import com.example.myhome.features.charge.ChargeRepository
import com.example.myhome.features.charge.models.SinglePaymentDocumentGetModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SinglePaymentDocumentGetUseCase @Inject constructor(
    private val chargeRepository: ChargeRepository
) {
    operator fun invoke(id: Int): Flow<SinglePaymentDocumentGetModel> {
        return chargeRepository.getSinglePaymentDocument(id)
    }
}
