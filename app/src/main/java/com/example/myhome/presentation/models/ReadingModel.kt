package com.example.myhome.presentation.models

import com.example.myhome.common.models.Adaptive
import com.example.myhome.common.models.DateConverter
import java.util.Date

data class ReadingUiModel(
    override val id: Int,
    val reading: Double,
    val consumption: Double,
    val readAt: Date,
    val unitName: String
): Adaptive, DateConverter {
    fun formattedDate(): String {
        return formatDate(readAt)
    }
}