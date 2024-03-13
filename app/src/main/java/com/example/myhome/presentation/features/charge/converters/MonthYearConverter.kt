package com.example.myhome.presentation.features.charge.converters

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface MonthYearConverter {
    private val dateFormat: SimpleDateFormat
        get() = SimpleDateFormat("MMMM yyyy", Locale("ru"))

    fun formatDate(date: Date, capFirst: Boolean = true): String {
        var formattedDate = dateFormat.format(date) ?: throw IllegalArgumentException("Неверный формат даты: $date")
        if (capFirst) {
            formattedDate = formattedDate.replaceFirstChar { it.uppercase() }
        }
        val firstSpaceIndex = formattedDate.indexOf(' ')
        return if (firstSpaceIndex != -1) {
            val firstWord = formattedDate.substring(0, firstSpaceIndex)
            val modifiedFirstWord = firstWord.dropLast(1) + "ь"
            modifiedFirstWord + formattedDate.substring(firstSpaceIndex)
        } else {
            formattedDate
        }
    }


}