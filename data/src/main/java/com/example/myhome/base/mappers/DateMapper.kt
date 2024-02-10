package com.example.myhome.base.mappers

import java.text.SimpleDateFormat
import java.util.Date

class DateMapper {
    fun mapyyyyMMdd(dateTimeString: String): Date {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        return dateFormat.parse(dateTimeString)
    }

}