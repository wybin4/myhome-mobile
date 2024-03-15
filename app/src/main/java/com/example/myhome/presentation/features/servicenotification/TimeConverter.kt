package com.example.myhome.presentation.features.servicenotification

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

interface TimeConverter {
    fun formatHHMM(date: Date): String {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(date)
    }
}
