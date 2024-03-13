package com.example.myhome.features.charge.models

data class DebtListItemModel (
    val spdId: Int,
    val originalDebt: Double,
    val outstandingDebt: Double
)