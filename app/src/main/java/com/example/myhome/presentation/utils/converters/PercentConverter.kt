package com.example.myhome.presentation.utils.converters

interface PercentConverter {
    fun formatDouble0F(value: Double): String {
        val valueString = "%.0f".format(value)
        return "$valueString%"
    }

    fun formatDouble1F(value: Double): String {
        val valueString = "%.1f".format(value)
        return "$valueString%"
    }
}