package com.example.myhome.features.meter.mappers

import com.example.myhome.features.meter.dtos.ReadingAddRequest
import com.example.myhome.features.meter.dtos.ReadingListItemResponse
import com.example.myhome.features.meter.models.ReadingAddModel
import com.example.myhome.features.meter.models.ReadingListItemModel

class ReadingRemoteMapper {
    fun mapListToDomain(readings: List<ReadingListItemResponse>): List<ReadingListItemModel> {
        return readings.map { mReading ->
            ReadingListItemModel(
                id = mReading.id,
                reading = mReading.reading,
                consumption = mReading.consumption,
                readAt = mReading.formatReadAt(),
            )
        }
    }

    fun mapToDomain(reading: ReadingListItemResponse): ReadingListItemModel {
        return reading.let {
            ReadingListItemModel(
                id = it.id,
                reading = it.reading,
                consumption = it.consumption,
                readAt = it.formatReadAt()
            )
        }
    }

    fun mapAddToRemote(reading: ReadingAddModel): ReadingAddRequest {
        return reading.let {
            ReadingAddRequest(
                meterId = it.meterId,
                reading = it.reading,
                readAt = it.readAt.toString()
            )
        }
    }
}