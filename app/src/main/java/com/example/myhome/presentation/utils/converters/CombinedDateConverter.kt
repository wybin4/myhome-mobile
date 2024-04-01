package com.example.myhome.presentation.utils.converters

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

interface CombinedDateConverter {
    fun formatDate(date: Date): String {
        val dateFormat = if (isCurrentYear(date)) {
            SimpleDateFormat("dd MMMM", Locale("ru"))
        } else {
            SimpleDateFormat("dd MMMM yyyy", Locale("ru"))
        }
        return dateFormat.format(date)
    }

    fun formatDateHuman(date: Date): String {
        val calendar = Calendar.getInstance()

        val today = calendar.clone() as Calendar
        val yesterday = calendar.clone() as Calendar

        if (isSameDay(date, today.time)) {
            return "Сегодня"
        }

        yesterday.add(Calendar.DAY_OF_YEAR, -1)
        if (isSameDay(date, yesterday.time)) {
            return "Вчера"
        }

        return formatDate(date)
    }

    private fun isSameDay(date1: Date, date2: Date): Boolean {
        val cal1 = Calendar.getInstance().apply { time = date1 }
        val cal2 = Calendar.getInstance().apply { time = date2 }
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }

    private fun isCurrentYear(date: Date): Boolean {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val messageCalendar = Calendar.getInstance().apply { time = date }
        val messageYear = messageCalendar.get(Calendar.YEAR)
        return currentYear == messageYear
    }
}