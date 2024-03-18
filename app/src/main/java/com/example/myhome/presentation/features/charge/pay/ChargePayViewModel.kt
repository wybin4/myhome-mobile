package com.example.myhome.presentation.features.charge.pay

import com.example.myhome.presentation.BaseDigitPickerViewModel
import com.example.myhome.presentation.features.charge.converters.MoneyConverter
import com.example.myhome.presentation.features.charge.models.ChargeGetToPayParcelableModel
import com.example.myhome.presentation.utils.pickers.CharPicker
import com.example.myhome.presentation.utils.pickers.IconPicker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChargePayViewModel @Inject constructor() : BaseDigitPickerViewModel(), IconPicker, MoneyConverter, CharPicker {
    lateinit var chargeParcelable: ChargeGetToPayParcelableModel

    override fun isCardLeftTextVisible(): Boolean = true

    override fun getCardLeftText(): String = "Кому"

    override fun isIcon(): Boolean = false

    override fun getIcon(): Int? = null

    override fun getIconText(): String = processString(chargeParcelable.managementCompanyName)

    override fun getTitle(): String = chargeParcelable.managementCompanyName

    override fun getSubtitle(): String = "Управляющая компания"

    override fun getRightTitle(): String = "Нужно оплатить"

    override fun getLeftTitle(): String = "Остаток долга"

    override fun getPrevValue(): Double = chargeParcelable.outstandingDebt

    override fun getRightText(): String {
        return formatDouble2F(chargeParcelable.outstandingDebt)
    }

    override fun getUnit(): String = "₽"
}