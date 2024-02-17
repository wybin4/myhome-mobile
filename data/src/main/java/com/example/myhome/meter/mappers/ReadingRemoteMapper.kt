package com.example.myhome.meter.mappers

import com.example.myhome.meter.dtos.ReadingAddDto
import com.example.myhome.meter.dtos.ReadingGetDto
import com.example.myhome.meter.models.ReadingAddModel
import com.example.myhome.meter.models.ReadingGetModel

class ReadingRemoteMapper {
    fun mapListToDomain(readings: List<ReadingGetDto>): List<ReadingGetModel> {
        return readings.map { mReading ->
            ReadingGetModel(
                id = mReading.id,
                reading = mReading.reading,
                consumption = mReading.consumption,
                readAt = mReading.formatReadAt(),
            )
        }
    }
    fun mapToDomain(reading: ReadingGetDto): ReadingGetModel {
        return reading.let {
            ReadingGetModel(
                id = it.id,
                reading = it.reading,
                consumption = it.consumption,
                readAt = it.formatReadAt()
            )
        }
    }
    fun mapAddToRemote(reading: ReadingAddModel): ReadingAddDto {
        return reading.let {
            ReadingAddDto(
                meterId = it.meterId,
                reading = it.reading,
                readAt = it.readAt.toString()
            )
        }
    }
}