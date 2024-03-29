package com.example.myhome.features.charge

import com.example.myhome.features.charge.dtos.ChargeListItemResponse
import com.example.myhome.features.charge.dtos.ChargeListRequest
import com.example.myhome.features.charge.dtos.PaymentListItemResponse
import com.example.myhome.features.charge.dtos.PaymentListRequest
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetRequest
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ChargeApiService {
    @POST("debt/get-charges")
    suspend fun listCharge(
        @Body request: ChargeListRequest
    ): List<ChargeListItemResponse>

    @POST("single-payment-document/check-single-payment-document")
    suspend fun getSinglePaymentDocument(
        @Body request: SinglePaymentDocumentGetRequest
    ): SinglePaymentDocumentGetResponse

    @POST("payment/get-payments")
    suspend fun listPayment(
        @Body request: PaymentListRequest
    ): List<PaymentListItemResponse>

}