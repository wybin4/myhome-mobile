package com.example.myhome.meter.mappers

import com.example.myhome.meter.dtos.ReadingAddDto
import com.example.myhome.meter.dtos.ReadingGetDto
import com.example.myhome.meter.models.ReadingAddModel
import com.example.myhome.meter.models.ReadingGetModel
import com.example.myhome.utils.converters.ReadingConverter

class ReadingRemoteMapper: ReadingConverter {
    fun mapListToDomain(readings: List<ReadingGetDto>): List<ReadingGetModel> {
        return readingListToDomain(readings)
    }

    fun mapAddToRemote(reading: ReadingAddModel): ReadingAddDto {
        return readingFromAddToRemote(reading)
    }
}