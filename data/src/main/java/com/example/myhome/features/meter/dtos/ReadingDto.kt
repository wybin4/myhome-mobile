package com.example.myhome.features.meter.dtos

import com.example.myhome.features.meter.models.MeterType
import com.example.myhome.models.DateConverter
import java.util.Date

data class ReadingAddRequest(
    val meterId: Int,
    val meterType: MeterType = MeterType.Individual,
    val reading: Double,
    val readAt: String
)

data class ReadingListItemResponse(
    val id: Int,
    val reading: Double,
    val consumption: Double,
    val readAt: String
): DateConverter {
    fun formatReadAt(): Date {
        return parseDate(readAt)
    }
}

data class ReadingListRequest(
    val meterId: Int
)