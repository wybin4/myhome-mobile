package com.example.myhome.features.charge.repositories

import androidx.paging.PagingData
import com.example.myhome.features.charge.dtos.ChargeChartListItemResponse
import com.example.myhome.features.charge.dtos.ChargeListItemResponse
import com.example.myhome.features.charge.dtos.DebtListItemResponse
import com.example.myhome.features.charge.dtos.PaymentListItemResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetResponse
import kotlinx.coroutines.flow.Flow

interface ChargeRepository {
    fun listCharge(): Flow<PagingData<ChargeListItemResponse>>
    fun listDebt(): Flow<List<DebtListItemResponse>>
    fun listChargeChart(): Flow<List<ChargeChartListItemResponse>>
    fun getSinglePaymentDocument(id: Int): Flow<SinglePaymentDocumentGetResponse>
    fun listPayment(spdId: Int): Flow<List<PaymentListItemResponse>>
}