package com.example.myhome.presentation.meter.get

import com.example.myhome.common.models.Adaptive
import com.example.myhome.common.models.DateConverter
import java.util.Date

data class ReadingUiModel(
    override val id: Int,
    val reading: Float,
    val consumption: Float,
    val readAt: Date,
    val unitName: String
): Adaptive, DateConverter {
    fun formattedDate(): String {
        return formatDate(readAt)
    }
}