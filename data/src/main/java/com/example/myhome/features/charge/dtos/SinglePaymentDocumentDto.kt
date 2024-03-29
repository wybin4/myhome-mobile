package com.example.myhome.features.charge.dtos

import com.example.myhome.models.DateConverter
import com.example.myhome.models.UserRole
import java.util.Date

enum class CalculationState {
    Started, DetailsCalculated, CorrectionsCalculated, Cancelled
}

data class SinglePaymentDocumentGetRequest(
    val id: Int
)

data class SinglePaymentDocumentGetResponse(
    val id: Int,
    val totalId: Int,
    val subscriberId: Int,
    val amount: Double,
    val debt: Double,
    val penalty: Double,
    val deposit: Double,
    val path: String,
    val createdAt: String,
    val status: CalculationState
): DateConverter {
    fun formatCreatedAt(): Date {
        return parseDate(createdAt)
    }
}