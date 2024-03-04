package com.example.myhome.meter.repositories

import com.example.myhome.meter.models.ReadingAddModel
import com.example.myhome.meter.models.ReadingGetModel
import kotlinx.coroutines.flow.Flow

interface ReadingRepository {
    fun addReading(reading: ReadingAddModel): Flow<Boolean>
    fun listReading(meterId: Int): Flow<List<ReadingGetModel>>
}