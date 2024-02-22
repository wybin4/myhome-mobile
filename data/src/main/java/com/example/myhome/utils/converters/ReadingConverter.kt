package com.example.myhome.utils.converters

import com.example.myhome.meter.dtos.ReadingAddDto
import com.example.myhome.meter.dtos.ReadingGetDto
import com.example.myhome.meter.models.ReadingAddModel
import com.example.myhome.meter.models.ReadingGetModel

interface ReadingConverter {
    fun readingListToDomain(readings: List<ReadingGetDto>): List<ReadingGetModel> {
        return readings.map { mReading ->
            ReadingGetModel(
                id = mReading.id,
                reading = mReading.reading,
                consumption = mReading.consumption,
                readAt = mReading.formatReadAt(),
            )
        }
    }

    fun readingToDomain(reading: ReadingGetDto): ReadingGetModel {
        return reading.let {
            ReadingGetModel(
                id = it.id,
                reading = it.reading,
                consumption = it.consumption,
                readAt = it.formatReadAt()
            )
        }
    }

    fun readingFromAddToRemote(reading: ReadingAddModel): ReadingAddDto {
        return reading.let {
            ReadingAddDto(
                meterId = it.meterId,
                reading = it.reading,
                readAt = it.readAt.toString()
            )
        }
    }
}