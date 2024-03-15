package com.example.myhome.testutils

import com.example.myhome.models.DateConverter
import java.util.Calendar
import java.util.Date

object DateDomainProvider: DateConverter {
    val date = Date()

    fun getDateString(): String {
        return formatDate(date)
    }

    private fun notIssuedDate(): Date {
        val currentDate = Calendar.getInstance()
        currentDate.add(Calendar.YEAR, 1)
        return currentDate.time ?: Date()
    }

    fun notIssuedDateString(): String {
        return formatDate(notIssuedDate())
    }

    private fun subtractDays(days: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -days)
        return calendar.time
    }

    fun subtractDaysString(days: Int): String {
        return formatDate(subtractDays(days))
    }

}