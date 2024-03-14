package com.example.myhome.presentation.utils.pickers

abstract class DigitPicker {
    abstract fun doubleToString(value: Double): String
    abstract fun calcResult(new: Double, prev: Double): String
    abstract fun addDigit(newVal: String, prevString: String): String

    fun stringToDouble(value: String): Double {
        return value.replace(',', '.').toDoubleOrNull() ?: 0.0
    }

    protected fun isResultReturn(
        newVal: String, result: String,
        digitsBeforeComma: Int, digitsAfterComma: Int
    ): String? {
        val commaCount = result.count { it == ',' }

        val parts = result.split(',')
        if (parts[0].length >= digitsBeforeComma && newVal != "," && commaCount < 1) {
            return result
        }
        if (commaCount > 0 && parts[1].length >= digitsAfterComma) {
            return result
        }
        if (commaCount > 0 && newVal == ",") {
            return result
        }
        return null
    }

    fun clearDigit(text: String): String? {
        if (text.length > 1) {
            return text.slice(0 until text.length - 1)
        } else if (text.length == 1 && text != "0") {
            return "0"
        }
        return null
    }


}