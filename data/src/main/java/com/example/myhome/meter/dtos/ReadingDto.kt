package com.example.myhome.meter.dtos

import com.example.myhome.common.models.DateConverter
import com.example.myhome.meter.models.MeterType
import java.util.Date

data class ReadingAddDto(
    val meterId: Int,
    val meterType: MeterType = MeterType.Individual,
    val reading: Double,
    val readAt: String
)

data class ReadingGetDto(
    val id: Int,
    val reading: Double,
    val consumption: Double,
    val readAt: String
): DateConverter {
    fun formatReadAt(): Date {
        return parseDate(readAt)
    }
}

data class ReadingListDto(
    val meterId: Int
)