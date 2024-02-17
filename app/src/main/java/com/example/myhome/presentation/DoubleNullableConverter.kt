package com.example.myhome.presentation

interface DoubleNullableConverter {
    fun x2doubleToString(value: Double?, another: String?): String {
        return if (value != null && another != null) {
            "$value $another"
        } else {
            doubleToString(value)
        }
    }
    fun doubleToString(value: Double?): String {
        return value?.toString() ?: "—"
    }
}