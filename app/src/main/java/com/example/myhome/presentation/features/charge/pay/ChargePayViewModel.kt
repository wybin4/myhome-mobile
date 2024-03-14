package com.example.myhome.presentation.features.charge.pay

import android.util.Log
import com.example.myhome.presentation.BaseDigitPickerViewModel
import com.example.myhome.presentation.features.charge.converters.MoneyConverter
import com.example.myhome.presentation.features.charge.models.ChargeGetToPayParcelableModel
import com.example.myhome.presentation.utils.pickers.IconPicker
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChargePayViewModel @Inject constructor(
//    private val readingAddUseCase: ReadingAddUseCase
) : BaseDigitPickerViewModel(), IconPicker, MoneyConverter {
    lateinit var сhargeParcelable : ChargeGetToPayParcelableModel
    override fun addNewValue(newValue: Double) {
        Log.e("addNewValue", newValue.toString())
//        viewModelScope.launch {
//            readingAddUseCase(
//                ReadingAddModel(
//                    сhargeId = сhargeParcelable.сhargeId,
//                    reading = newReading,
//                    readAt = Date()
//                )
//            )
//                .asNetworkResult()
//                .collect {
//                    it.asAddResource(_dataAddState)
//                }
//        }
    }

    override fun isCardLeftTextVisible(): Boolean = true

    override fun getCardLeftText(): String = "Кому"

    override fun isIcon(): Boolean = false

    override fun getIcon(): Int? = null

    override fun getIconText(): String = getFirstLetterAfterQuote(сhargeParcelable.managementCompanyName)

    private fun getFirstLetterAfterQuote(input: String): String {
        val quoteCharacters = charArrayOf('\'', '"', '«')
        val quoteIndex = input.indexOfAny(quoteCharacters)
        return if (quoteIndex != -1 && quoteIndex < input.length - 1) {
            val substring = input.substring(quoteIndex + 1)
            substring.firstOrNull { it.isLetter() }
        } else {
            "?"
        }.toString()
    }

    override fun getTitle(): String = сhargeParcelable.managementCompanyName

    override fun getSubtitle(): String = "Управляющая компания"

    override fun getRightTitle(): String = "Нужно оплатить"

    override fun getLeftTitle(): String = "Остаток долга"

    override fun getPrevValue(): Double = сhargeParcelable.outstandingDebt

    override fun getRightText(): String {
        return formatDouble2F(сhargeParcelable.outstandingDebt)
    }

    override fun getUnit(): String = "₽"
}