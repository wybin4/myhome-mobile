package com.example.myhome.features.charge

import com.example.myhome.ConstantsData.Companion.CHARGE_PAGE_SIZE
import com.example.myhome.features.charge.dtos.ChargeChartListItemResponse
import com.example.myhome.features.charge.dtos.ChargeChartListRequest
import com.example.myhome.features.charge.dtos.DebtListItemResponse
import com.example.myhome.features.charge.dtos.DebtListRequest
import com.example.myhome.features.charge.dtos.PaymentListItemResponse
import com.example.myhome.features.charge.dtos.PaymentListRequest
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetItemResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetRequest

class ChargeStorage(private val chargeApiService: ChargeApiService) {
    suspend fun listPayment(spdId: Int): List<PaymentListItemResponse> {
        val request = PaymentListRequest(spdId)
        return chargeApiService.listPayment(request).payments
    }

    suspend fun getSinglePaymentDocument(id: Int): SinglePaymentDocumentGetItemResponse {
        val request = SinglePaymentDocumentGetRequest(id)
        return chargeApiService.getSinglePaymentDocument(request).singlePaymentDocument
    }

    suspend fun listDebt(): List<DebtListItemResponse> {
        val request = DebtListRequest(userId = 1)
        return chargeApiService.listDebt(request).debts
    }

    suspend fun listChargeChart(): List<ChargeChartListItemResponse> {
        val request = ChargeChartListRequest(userId = 1, count = CHARGE_PAGE_SIZE)
        return chargeApiService.listChargeChart(request).charges
    }
}