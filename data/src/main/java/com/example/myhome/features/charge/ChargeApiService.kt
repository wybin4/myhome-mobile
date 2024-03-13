package com.example.myhome.features.charge

import com.example.myhome.features.charge.dtos.DebtListItemResponse
import com.example.myhome.features.charge.dtos.DebtListRequest
import com.example.myhome.features.charge.dtos.PaymentListItemResponse
import com.example.myhome.features.charge.dtos.PaymentListRequest
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetRequest
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentListItemResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentListRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ChargeApiService {
    @POST("debt/get-debts")
    suspend fun listDebt(
        @Body request: DebtListRequest
    ): List<DebtListItemResponse>

    @POST("single-payment-document/get-single-payment-documents-by-user")
    suspend fun listSinglePaymentDocument(
        @Body request: SinglePaymentDocumentListRequest
    ): List<SinglePaymentDocumentListItemResponse>

    @POST("single-payment-document/check-single-payment-document")
    suspend fun getSinglePaymentDocument(
        @Body request: SinglePaymentDocumentGetRequest
    ): SinglePaymentDocumentGetResponse

    @POST("payment/get-payments")
    suspend fun listPayment(
        @Body request: PaymentListRequest
    ): List<PaymentListItemResponse>

}