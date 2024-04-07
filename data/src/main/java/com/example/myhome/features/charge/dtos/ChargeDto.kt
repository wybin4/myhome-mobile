package com.example.myhome.features.charge.dtos

import com.example.myhome.models.DateConverter
import com.example.myhome.models.MetaRequest
import java.util.Date

enum class AmountChange {
    Positive, Negative, None
}

data class ChargeListRequest(
    val meta: MetaRequest
)

data class ChargeListResponse(
    val charges: List<ChargeListItemResponse>
)

data class ChargeListItemResponse(
    val id: Int,
    val originalDebt: Double,
    val outstandingDebt: Double,
    val apartmentName: String,
    val apartmentId: Int,
    val mcName: String,
    val mcCheckingAccount: String,
    val createdAt: String,
    var percent: Double,
    val amountChange: AmountChange
): DateConverter {
    fun formatCreatedAt(): Date {
        return parseDate(createdAt)
    }
}

data class ChargeChartListRequest(
    val count: Int,
)

data class ChargeChartListResponse(
    val charges: List<ChargeChartListItemResponse>
)

data class ChargeChartListItemResponse(
    val id: Int,
    val amount: Double,
    val createdAt: String,
    val apartmentId: Int,
    val apartmentName: String
): DateConverter {
    fun formatCreatedAt(): Date {
        return parseDate(createdAt)
    }
}