package com.example.myhome.testutils.providers

import com.example.myhome.models.DateConverter
import java.util.Calendar
import java.util.Date

class DateProvider: DateConverter {
    private val calendar: Calendar = Calendar.getInstance()
    private val year = calendar.get(Calendar.YEAR)
    private val month = calendar.get(Calendar.MONTH)
    private val dayOfMonth = 15

    val expectedDateTime: Date = calendar.apply {
        set(year, month + 1, dayOfMonth)
    }.time
    val expectedDate = formatDate(expectedDateTime)
}