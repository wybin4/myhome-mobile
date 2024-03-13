package com.example.myhome.presentation.features.charge

import android.util.Log
import com.example.myhome.features.charge.models.DebtListItemModel
import com.example.myhome.presentation.features.charge.models.AmountChange

interface ChargeCalculator {
    fun calculateAmountChange(currDebt: DebtListItemModel, prevDebt: DebtListItemModel?): AmountChange {
        return if (prevDebt != null) {
            val debtDiff = currDebt.originalDebt - prevDebt.originalDebt
            if (debtDiff > 0) AmountChange.Positive else if (debtDiff < 0) AmountChange.Negative else AmountChange.None
        } else {
            AmountChange.None // если нет предыдущего долга
        }
    }

    fun calculatePercent(currDebt: DebtListItemModel?, prevDebt: DebtListItemModel?): Double {
        return if (prevDebt != null) {
            val prevDebtAmount = prevDebt.originalDebt
            val debtDiff = (currDebt?.originalDebt ?: 0.0) - prevDebtAmount
            if (prevDebtAmount != 0.0) debtDiff / prevDebtAmount * 100 else 0.0 // вычисляем процент
        } else {
            0.0 // если нет предыдущего долга, процент равен нулю
        }
    }
}
