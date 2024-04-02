package com.example.myhome.models

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

interface DateTimeConverter {
    private val dateFormat: SimpleDateFormat
        get() {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            return formatter
        }
    private val outputFormat: SimpleDateFormat
        get() = SimpleDateFormat("HH:mm dd.MM.yyyy", Locale.getDefault())

    fun formatDateTime(date: Date): String {
        return outputFormat.format(date) ?: throw IllegalArgumentException("Неверный формат даты: $date")
    }

    fun parseDateTime(dateString: String): Date {
        return try {
            dateFormat.parse(dateString) ?: throw IllegalArgumentException("Неверный формат даты: $dateString")
        } catch (e: Exception) {
            Date()
        }
    }
}