package com.example.myhome.features.charge.models

import java.util.Date

data class SinglePaymentDocumentListItemModel (
    val id: Int,
    val apartmentName: String,
    val apartmentId: Int,
    val managementCompanyName: String,
    val managementCompanyCheckingAccount: String,
    val createdAt: Date
)

data class SinglePaymentDocumentGetModel (
    val id: Int,
    val amount: Double,
    val debt: Double,
    val penalty: Double,
    val path: String,
    val createdAt: Date
)