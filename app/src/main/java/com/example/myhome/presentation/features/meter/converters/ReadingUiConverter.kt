package com.example.myhome.presentation.features.meter.converters

import com.example.myhome.features.meter.dtos.ReadingListItemResponse
import com.example.myhome.presentation.features.meter.ReadingUiModel

interface ReadingUiConverter {
    fun readingListToUi(readings: List<ReadingListItemResponse>, unitName: String): List<ReadingUiModel> {
        return readings.map {
            ReadingUiModel(
                id = it.id,
                reading = it.reading,
                readAt = it.formatReadAt(),
                consumption = it.consumption,
                unitName = unitName
            )
        }
    }

}