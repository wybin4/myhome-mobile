package com.example.myhome.models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface DateTimeConverter {
    private val dateFormat: SimpleDateFormat
        get() = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault())

    fun formatDateTime(date: Date): String {
        return dateFormat.format(date) ?: throw IllegalArgumentException("Неверный формат даты: $date")
    }

    fun parseDateTime(dateString: String): Date {
        return try {
            dateFormat.parse(dateString) ?: throw IllegalArgumentException("Неверный формат даты: $dateString")
        } catch (e: Exception) {
            Date()
        }
    }
}