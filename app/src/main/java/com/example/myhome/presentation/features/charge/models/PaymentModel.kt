package com.example.myhome.presentation.features.charge.models

import com.example.myhome.presentation.utils.converters.CombinedDateConverter
import com.example.myhome.presentation.models.Adaptive
import java.util.Date

data class PaymentUiModel(
    override val id: Int,
    val amount: Double,
    val payedAt: Date
) : Adaptive, CombinedDateConverter {
    fun getFormattedPayedAt(): String {
        return formatDate(payedAt)
    }

    fun getFormattedAmount(): String {
        return "Оплата в размере " + String.format("%.2f", amount) + " ₽"
    }
}