package com.example.myhome.features.charge.dtos

import com.example.myhome.models.DateConverter
import com.example.myhome.models.UserRole
import java.util.Date

data class SinglePaymentDocumentListRequest(
    val userId: Int,
    val userRole: UserRole = UserRole.Owner,
    val withoutAttachments: Boolean = true
)

data class SinglePaymentDocumentListItemResponse(
    val id: Int,
    val apartmentName: String,
    val apartmentId: Int,
    val mcName: String,
    val mcCheckingAccount: String,
    val createdAt: String
): DateConverter {
    fun formatCreatedAt(): Date {
        return parseDate(createdAt)
    }
}
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