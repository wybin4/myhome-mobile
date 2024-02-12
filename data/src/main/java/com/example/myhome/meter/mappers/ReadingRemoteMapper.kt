package com.example.myhome.meter.mappers

import com.example.myhome.base.mappers.DateMapper
import com.example.myhome.meter.dtos.ReadingGetDto
import com.example.myhome.meter.models.ReadingGetModel

class ReadingRemoteMapper(private val dateMapper: DateMapper) {
    fun mapListToDomain(readings: List<ReadingGetDto>): List<ReadingGetModel> {
        return readings.map { mReading ->
            ReadingGetModel(
                id = mReading.id,
                reading = mReading.reading,
                consumption = mReading.consumption,
                readAt = mReading.readAt.let { dateMapper.mapyyyyMMdd(it) },
            )
        }
    }
}