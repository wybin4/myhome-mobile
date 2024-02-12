package com.example.myhome.common.models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface DateConverter {
    private val dateFormat: SimpleDateFormat
        get() = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    fun formatDate(date: Date): String {
        return dateFormat.format(date) ?: throw IllegalArgumentException("Неверный формат даты: $date")
    }

    fun parseDate(dateString: String): Date {
        return try {
            dateFormat.parse(dateString) ?: throw IllegalArgumentException("Неверный формат даты: $dateString")
        } catch (e: Exception) {
            Date()
        }
    }
}