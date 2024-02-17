package com.example.myhome

import com.example.myhome.common.models.DateConverter
import java.util.Date

class DateMapper: DateConverter {
    fun mapyyyyMMdd(dateString: String): Date {
        return parseDate(dateString)
    }
}