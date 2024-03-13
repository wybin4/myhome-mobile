package com.example.myhome.features.charge.models

import java.util.Date

data class PaymentListItemModel (
    val id: Int,
    val amount: Double,
    val payedAt: Date
)