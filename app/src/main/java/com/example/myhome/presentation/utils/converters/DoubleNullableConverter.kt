package com.example.myhome.presentation.utils.converters

interface DoubleNullableConverter {
    fun x2doubleToString(value: Double, another: String?): String {
        return if (value != 0.0 && another != null) {
            "$value $another"
        } else {
            doubleToString(value)
        }
    }
    fun doubleToString(value: Double): String {
        return if (value != 0.0) {
            value.toString()
        } else {
            "—"
        }
    }
}