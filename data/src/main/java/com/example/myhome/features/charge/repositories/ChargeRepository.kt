package com.example.myhome.features.charge.repositories

import com.example.myhome.features.charge.dtos.DebtListItemResponse
import com.example.myhome.features.charge.dtos.PaymentListItemResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentListItemResponse
import kotlinx.coroutines.flow.Flow

interface ChargeRepository {
    fun listCharge(): Flow<Pair<List<SinglePaymentDocumentListItemResponse>, List<DebtListItemResponse>>>
    fun getSinglePaymentDocument(id: Int): Flow<SinglePaymentDocumentGetResponse>
    fun listPayment(spdId: Int): Flow<List<PaymentListItemResponse>>
}