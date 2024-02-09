package com.example.myhome.data.mappers.remote

import com.example.myhome.data.dtos.ReadingAddDto
import com.example.myhome.data.dtos.ReadingGetDto
import com.example.myhome.data.mappers.DateMapper
import com.example.myhome.domain.models.ReadingAddModel
import com.example.myhome.domain.models.ReadingGetModel

class ReadingRemoteMapper(private val dateMapper: DateMapper) {
    fun mapAddToRemote(mReading: ReadingAddModel): ReadingAddDto {
        return mReading.run {
            ReadingAddDto(
                id = id,
                meterId = meterId,
                reading = reading,
                readAt = readAt.toString()
            )
        }
    }
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
    fun mapToDomain(mReading: ReadingGetDto): ReadingGetModel {
        return mReading.run {
            ReadingGetModel(
                id = id,
                reading = reading,
                consumption = consumption,
                readAt = readAt.let { dateMapper.mapyyyyMMdd(it) },
            )
        }
    }
}