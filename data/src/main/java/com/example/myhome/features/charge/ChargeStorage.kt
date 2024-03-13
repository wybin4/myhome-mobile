package com.example.myhome.features.charge

import com.example.myhome.features.charge.dtos.DebtListItemResponse
import com.example.myhome.features.charge.dtos.DebtListRequest
import com.example.myhome.features.charge.dtos.PaymentListItemResponse
import com.example.myhome.features.charge.dtos.PaymentListRequest
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetRequest
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentListItemResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentListRequest

class ChargeStorage(private val chargeApiService: ChargeApiService) {
    suspend fun listDebt(): List<DebtListItemResponse> {
        val request = DebtListRequest(ownerId = 1)
        return chargeApiService.listDebt(request)
    }

    suspend fun listPayment(spdId: Int): List<PaymentListItemResponse> {
        val request = PaymentListRequest(spdId)
        return chargeApiService.listPayment(request)
    }

    suspend fun listSinglePaymentDocument(): List<SinglePaymentDocumentListItemResponse> {
        val request = SinglePaymentDocumentListRequest(userId = 1)
        return chargeApiService.listSinglePaymentDocument(request)
    }

    suspend fun getSinglePaymentDocument(id: Int): SinglePaymentDocumentGetResponse {
        val request = SinglePaymentDocumentGetRequest(id)
        return chargeApiService.getSinglePaymentDocument(request)
    }
}