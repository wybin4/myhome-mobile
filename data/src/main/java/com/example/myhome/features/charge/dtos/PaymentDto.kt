package com.example.myhome.features.charge.dtos

import com.example.myhome.models.DateConverter
import java.util.Date

data class PaymentListRequest(
    val singlePaymentDocumentId: Int
)

data class PaymentListItemResponse(
    val id: Int,
    val amount: Double,
    val singlePaymentDocumentId: Int,
    val payedAt: String
): DateConverter {
    fun formatPayedAt(): Date {
        return parseDate(payedAt)
    }
}