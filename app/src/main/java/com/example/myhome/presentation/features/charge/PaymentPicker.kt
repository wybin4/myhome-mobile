package com.example.myhome.presentation.features.charge

import com.example.myhome.presentation.utils.pickers.DigitPicker

class PaymentPicker: DigitPicker() {
    override fun doubleToString(value: Double): String {
        return String.format("%.2f", value)
    }

    override fun addDigit(newVal: String, prevString: String): String {
        var result = prevString
        if (prevString == "0" && newVal != ",") {
            result = ""
        }

        val returnResult = isResultReturn(newVal, result, 5, 2)
        if (returnResult != null) {
            return result
        }

        return result + newVal
    }

    override fun calcResult(new: Double, prev: Double): String {
        return if (prev > new) {
            doubleToString(prev - new)
        } else {
            "0"
        }
    }

}