package com.example.myhome.features.charge

import com.example.myhome.features.charge.dtos.ChargeChartListRequest
import com.example.myhome.features.charge.dtos.ChargeChartListResponse
import com.example.myhome.features.charge.dtos.ChargeListRequest
import com.example.myhome.features.charge.dtos.ChargeListResponse
import com.example.myhome.features.charge.dtos.DebtListResponse
import com.example.myhome.features.charge.dtos.PaymentListRequest
import com.example.myhome.features.charge.dtos.PaymentListResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetRequest
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChargeApiService {
    @POST("debt/get-charges")
    suspend fun listCharge(
        @Body request: ChargeListRequest
    ): ChargeListResponse

    @POST("single-payment-document/check-single-payment-document")
    suspend fun getSinglePaymentDocument(
        @Body request: SinglePaymentDocumentGetRequest
    ): SinglePaymentDocumentGetResponse

    @POST("debt/get-debts")
    suspend fun listDebt(): DebtListResponse

    @POST("debt/get-charge-chart")
    suspend fun listChargeChart(
        @Body request: ChargeChartListRequest
    ): ChargeChartListResponse

    @POST("payment/get-payments")
    suspend fun listPayment(
        @Body request: PaymentListRequest
    ): PaymentListResponse

}