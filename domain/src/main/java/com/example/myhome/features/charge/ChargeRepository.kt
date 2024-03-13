package com.example.myhome.features.charge

import com.example.myhome.features.charge.models.DebtListItemModel
import com.example.myhome.features.charge.models.PaymentListItemModel
import com.example.myhome.features.charge.models.SinglePaymentDocumentGetModel
import com.example.myhome.features.charge.models.SinglePaymentDocumentListItemModel
import kotlinx.coroutines.flow.Flow

interface ChargeRepository {
    fun listSinglePaymentDocument(): Flow<List<SinglePaymentDocumentListItemModel>>
    fun getSinglePaymentDocument(id: Int): Flow<SinglePaymentDocumentGetModel>
    fun listDebt(): Flow<List<DebtListItemModel>>
    fun listPayment(spdId: Int): Flow<List<PaymentListItemModel>>
}