package com.example.myhome.presentation.features.servicenotification

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

interface TimeConverter {
    fun formatHHMM(date: Date): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val timeZone = TimeZone.getDefault()
        dateFormat.timeZone = timeZone
        return dateFormat.format(date)
    }
}
