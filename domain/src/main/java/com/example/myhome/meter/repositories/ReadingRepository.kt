package com.example.myhome.meter.repositories

import com.example.myhome.meter.models.ReadingAddModel
import com.example.myhome.meter.models.ReadingGetModel
import kotlinx.coroutines.flow.Flow

interface ReadingRepository {
    suspend fun addReading(reading: ReadingAddModel)
    fun listReading(meterId: Int): Flow<List<ReadingGetModel>>
}