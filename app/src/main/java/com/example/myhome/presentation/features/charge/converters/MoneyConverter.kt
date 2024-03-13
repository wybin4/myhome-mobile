package com.example.myhome.presentation.features.charge.converters

interface MoneyConverter {
    fun formatDouble2F(value: Double): String {
        return String.format("%.2f", value) + "₽"
    }
}