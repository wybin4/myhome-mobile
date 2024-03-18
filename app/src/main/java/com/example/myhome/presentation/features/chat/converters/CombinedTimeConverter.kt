package com.example.myhome.presentation.features.chat.converters

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

interface CombinedTimeConverter {
    fun formatTime(date: Date): String {
        val currentTime = Calendar.getInstance()
        val messageTime = Calendar.getInstance().apply { time = date }

        val timeFormat = if (currentTime[Calendar.YEAR] == messageTime[Calendar.YEAR] &&
            currentTime[Calendar.DAY_OF_YEAR] == messageTime[Calendar.DAY_OF_YEAR]
        ) {
            SimpleDateFormat("HH:mm", Locale("ru"))
        } else if (currentTime[Calendar.YEAR] == messageTime[Calendar.YEAR]) {
            SimpleDateFormat("dd MMM", Locale("ru"))
        } else {
            SimpleDateFormat("dd MMM yyyy", Locale("ru"))
        }
        return timeFormat.format(date)
    }
}