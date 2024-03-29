package com.example.myhome.features.charge

import com.example.myhome.features.charge.ChargePagingSource.Companion.CHARGE_PAGE_SIZE
import com.example.myhome.features.charge.dtos.ChargeChartListItemResponse
import com.example.myhome.features.charge.dtos.ChargeChartListRequest
import com.example.myhome.features.charge.dtos.DebtListItemResponse
import com.example.myhome.features.charge.dtos.DebtListRequest
import com.example.myhome.features.charge.dtos.PaymentListItemResponse
import com.example.myhome.features.charge.dtos.PaymentListRequest
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetRequest
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetResponse

class ChargeStorage(private val chargeApiService: ChargeApiService) {
    suspend fun listPayment(spdId: Int): List<PaymentListItemResponse> {
        val request = PaymentListRequest(spdId)
        return chargeApiService.listPayment(request)
    }

    suspend fun getSinglePaymentDocument(id: Int): SinglePaymentDocumentGetResponse {
        val request = SinglePaymentDocumentGetRequest(id)
        return chargeApiService.getSinglePaymentDocument(request)
    }

    suspend fun listDebt(): List<DebtListItemResponse> {
        val request = DebtListRequest(ownerId = 1)
        return chargeApiService.listDebt(request)
    }

    suspend fun listChargeChart(): List<ChargeChartListItemResponse> {
        val request = ChargeChartListRequest(ownerId = 1, count = CHARGE_PAGE_SIZE)
        return chargeApiService.listChargeChart(request)
    }
}