package com.example.myhome.features.meter.repositories

import com.example.myhome.features.meter.dtos.ReadingAddRequest
import com.example.myhome.features.meter.dtos.ReadingListItemResponse
import kotlinx.coroutines.flow.Flow

interface ReadingRepository {
    fun addReading(reading: ReadingAddRequest): Flow<Boolean>
    fun listReading(meterId: Int): Flow<List<ReadingListItemResponse>>
}