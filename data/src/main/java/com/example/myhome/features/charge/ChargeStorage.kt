package com.example.myhome.features.charge

import com.example.myhome.features.charge.dtos.ChargeListItemResponse
import com.example.myhome.features.charge.dtos.ChargeListRequest
import com.example.myhome.features.charge.dtos.PaymentListItemResponse
import com.example.myhome.features.charge.dtos.PaymentListRequest
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetRequest
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetResponse

class ChargeStorage(private val chargeApiService: ChargeApiService) {
    suspend fun listCharge(): List<ChargeListItemResponse> {
        val request = ChargeListRequest(userId = 1)
        return chargeApiService.listCharge(request)
    }

    suspend fun listPayment(spdId: Int): List<PaymentListItemResponse> {
        val request = PaymentListRequest(spdId)
        return chargeApiService.listPayment(request)
    }

    suspend fun getSinglePaymentDocument(id: Int): SinglePaymentDocumentGetResponse {
        val request = SinglePaymentDocumentGetRequest(id)
        return chargeApiService.getSinglePaymentDocument(request)
    }
}