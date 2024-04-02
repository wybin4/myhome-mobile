package com.example.myhome.models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface DateConverter {
    val dateFormat: SimpleDateFormat
        get() = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val outputFormat: SimpleDateFormat
        get() = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

    fun formatDate(date: Date): String {
        return outputFormat.format(date) ?: throw IllegalArgumentException("Неверный формат даты: $date")
    }

    fun formatDateDash(date: Date): String {
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