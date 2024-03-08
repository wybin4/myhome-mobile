package com.example.myhome.testutils.providers

import java.util.Calendar
import java.util.Date

object DateDomainProvider {
    val date = Date()

    fun subtractDays(days: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -days)
        return calendar.time
    }

}