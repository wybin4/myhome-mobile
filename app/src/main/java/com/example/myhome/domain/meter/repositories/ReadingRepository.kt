package com.example.myhome.domain.meter.repositories

import com.example.myhome.domain.meter.models.ReadingAddModel
import com.example.myhome.domain.meter.models.ReadingGetModel
import kotlinx.coroutines.flow.Flow

interface ReadingRepository {
    fun addReading(reading: ReadingAddModel): Flow<ReadingGetModel>
    fun listReading(meterId: Int): Flow<List<ReadingGetModel>>
}