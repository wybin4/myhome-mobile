package com.example.myhome.presentation.features.charge

import androidx.fragment.app.FragmentActivity
import com.example.myhome.R
import com.example.myhome.presentation.features.charge.models.AmountChange
import com.example.myhome.presentation.features.charge.models.ChargeUiModel
import com.example.myhome.presentation.utils.pickers.ColorPicker
import com.example.myhome.presentation.utils.pickers.ThemePicker

class ChargeColorPicker(
    private val context: FragmentActivity
): ColorPicker {
    fun getAmountChangeColor(charge: ChargeUiModel): Int {
        return when (charge.amountChange) {
            AmountChange.Positive -> getColor(context, R.color.red)
            AmountChange.Negative -> getColor(context, R.color.green)
            else -> getColor(context, R.color.transparent)
        }
    }
}