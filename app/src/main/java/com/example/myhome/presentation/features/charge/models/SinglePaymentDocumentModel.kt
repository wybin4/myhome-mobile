package com.example.myhome.presentation.features.charge.models

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import com.example.myhome.features.charge.dtos.AmountChange
import com.example.myhome.presentation.features.charge.converters.MoneyConverter
import com.example.myhome.presentation.features.charge.converters.MonthYearConverter
import com.example.myhome.presentation.models.AdaptiveInt
import java.util.Date

data class SinglePaymentDocumentUiModel(
    val id: Int,
    val amount: Double,
    val debt: Double,
    val penalty: Double,
    val path: String,
    val periodName: String
): MoneyConverter {
    fun formatAmount(): String {
        return formatDouble2F(amount)
    }

    fun formatDebt(): String {
        return formatDouble2F(debt)
    }

    fun formatPenalty(): String {
        return formatDouble2F(penalty)
    }
}

data class DebtUiModel(
    override val id: Int,
    val outstandingDebt: Double,
    val createdAt: Date,
    val apartmentName: String
) : AdaptiveInt, MoneyConverter, MonthYearConverter {
    fun getText(): SpannableStringBuilder {
        val formattedCreatedAt = formatDate(createdAt, false)
        val formattedOutstandingDebt = formatDouble2F(outstandingDebt)
        val formattedApartmentName = apartmentName.replaceFirstChar { it.lowercase() }

        val text = "Важно! У вас есть неоплаченная задолженность по квитанции за $formattedCreatedAt, $formattedApartmentName - "
        val boldText = SpannableStringBuilder(formattedOutstandingDebt)
        boldText.setSpan(
            StyleSpan(Typeface.BOLD),
            0,
            boldText.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        return SpannableStringBuilder(text).append(boldText)
    }

}

data class SpdDebtRelationListItem(
    val spdId: Int,
    val createdAt: Date,
    val percent: Double,
    val amountChange: AmountChange,
    val originalDebt: Double,
    val outstandingDebt: Double
)