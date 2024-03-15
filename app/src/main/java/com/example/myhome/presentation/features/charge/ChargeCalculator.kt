package com.example.myhome.presentation.features.charge

import com.example.myhome.features.charge.dtos.DebtListItemResponse
import com.example.myhome.presentation.features.charge.models.AmountChange

interface ChargeCalculator {
    fun calculateAmountChange(currDebt: DebtListItemResponse, prevDebt: DebtListItemResponse?): AmountChange {
        return if (prevDebt != null) {
            val debtDiff = currDebt.originalDebt - prevDebt.originalDebt
            if (debtDiff > 0) AmountChange.Positive else if (debtDiff < 0) AmountChange.Negative else AmountChange.None
        } else {
            AmountChange.None // если нет предыдущего долга
        }
    }

    fun calculatePercent(currDebt: DebtListItemResponse?, prevDebt: DebtListItemResponse?): Double {
        return if (prevDebt != null) {
            val prevDebtAmount = prevDebt.originalDebt
            val debtDiff = (currDebt?.originalDebt ?: 0.0) - prevDebtAmount
            if (prevDebtAmount != 0.0) debtDiff / prevDebtAmount * 100 else 0.0 // вычисляем процент
        } else {
            0.0 // если нет предыдущего долга, процент равен нулю
        }
    }
}
