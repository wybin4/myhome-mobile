package com.example.myhome.features.charge.repositories

import com.example.myhome.features.charge.dtos.ChargeListItemResponse
import com.example.myhome.features.charge.dtos.PaymentListItemResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetResponse
import kotlinx.coroutines.flow.Flow

interface ChargeRepository {
    fun listCharge(): Flow<List<ChargeListItemResponse>>
    fun getSinglePaymentDocument(id: Int): Flow<SinglePaymentDocumentGetResponse>
    fun listPayment(spdId: Int): Flow<List<PaymentListItemResponse>>
}