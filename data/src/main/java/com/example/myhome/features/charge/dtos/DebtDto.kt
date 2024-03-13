package com.example.myhome.features.charge.dtos

data class DebtListRequest(
    val ownerId: Int
)

data class DebtListItemResponse(
    val singlePaymentDocumentId: Int,
    val originalDebt: Double,
    val outstandingDebt: Double
)