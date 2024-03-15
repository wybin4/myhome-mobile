package com.example.myhome.presentation.utils.converters

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface CombinedDateConverter {
    fun formatDate(date: Date): String {
        val dateFormat: SimpleDateFormat = if (isCurrentYear(date)) {
            SimpleDateFormat("dd MMMM", Locale("ru"))
        } else {
            SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
        }
        return dateFormat.format(date)
    }

    private fun isCurrentYear(date: Date): Boolean {
        val yearFormat = SimpleDateFormat("yyyy")
        val currentYear = yearFormat.format(Date())
        val dateYear = yearFormat.format(date)
        return currentYear == dateYear
    }

}