package com.example.myhome.presentation.features.charge.models

import com.example.myhome.presentation.utils.converters.CombinedDateConverter
import com.example.myhome.presentation.models.AdaptiveInt
import java.util.Date

data class PaymentUiModel(
    override val id: Int,
    val amount: Double,
    val payedAt: Date
) : AdaptiveInt, CombinedDateConverter {
    fun getFormattedPayedAt(): String {
        return formatDate(payedAt)
    }

    fun getFormattedAmount(): String {
        return "Оплачено " + String.format("%.2f", amount) + " ₽"
    }
}