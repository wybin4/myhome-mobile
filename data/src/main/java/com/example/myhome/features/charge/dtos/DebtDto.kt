package com.example.myhome.features.charge.dtos

import com.example.myhome.models.DateConverter
import java.util.Date

data class DebtListRequest (
    val ownerId: Int
)

data class DebtListItemResponse (
    val id: Int,
    val outstandingDebt: Double,
    val createdAt: String,
    val apartmentName: String
): DateConverter {
    fun formatCreatedAt(): Date {
        return parseDate(createdAt)
    }
}