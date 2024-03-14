package com.example.myhome.presentation.features.meter

import com.example.myhome.presentation.utils.pickers.DigitPicker

class ReadingPicker: DigitPicker() {
    override fun doubleToString(value: Double): String {
        return String.format("%.3f", value)
    }

    override fun addDigit(newVal: String, prevString: String): String {
        var result = prevString
        if (prevString == "0" && newVal != ",") {
            result = ""
        }

        val returnResult = isResultReturn(newVal, result, 3, 3)
        if (returnResult != null) {
            return result
        }

        return result + newVal
    }

    override fun calcResult(new: Double, prev: Double): String {
        return if (new > prev) {
            doubleToString(new - prev)
        } else {
            "0"
        }
    }

}