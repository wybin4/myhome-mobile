package com.example.myhome.features.charge.repositories

import com.example.myhome.features.charge.ChargeStorage
import com.example.myhome.features.charge.dtos.DebtListItemResponse
import com.example.myhome.features.charge.dtos.PaymentListItemResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentListItemResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class  ChargeRepositoryImpl(
    private val chargeStorage: ChargeStorage
): ChargeRepository {
    override fun listCharge(): Flow<Pair<List<SinglePaymentDocumentListItemResponse>, List<DebtListItemResponse>>> = flow {
        val debts = chargeStorage.listDebt()
        val spds = chargeStorage.listSinglePaymentDocument()
        emit(Pair(spds, debts))
    }

    override fun getSinglePaymentDocument(id: Int): Flow<SinglePaymentDocumentGetResponse> = flow {
        emit(chargeStorage.getSinglePaymentDocument(id))
    }

    override fun listPayment(spdId: Int): Flow<List<PaymentListItemResponse>> = flow {
        emit(chargeStorage.listPayment(spdId))
    }

}
