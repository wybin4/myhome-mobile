package com.example.myhome.presentation.utils.pickers

class DigitPicker {
    fun doubleToString(value: Double): String {
        return String.format("%.3f", value)
    }

    fun stringToDouble(value: String): Double {
        return value.replace(',', '.').toDoubleOrNull() ?: 0.0
    }

    fun addDigit(newVal: String, prevString: String): String {
        var result = prevString
        if (prevString == "0" && newVal != ",") {
            result = ""
        }

        val commaCount = result.count { it == ',' }

        val parts = result.split(',')
        if (parts[0].length >= 3 && newVal != "," && commaCount < 1) {
            return result
        }
        if (commaCount > 0 && parts[1].length >= 3) {
            return result
        }
        if (commaCount > 0 && newVal == ",") {
            return result
        }

        return result + newVal
    }

    fun clearDigit(text: String): String? {
        if (text.length > 1) {
            return text.slice(0 until text.length - 1)
        } else if (text.length == 1 && text != "0") {
            return "0"
        }
        return null
    }

    fun calcConsumption(new: Double, prev: Double): String {
        return if (new > prev) {
            doubleToString(new - prev)
        } else {
            "0"
        }
    }
}