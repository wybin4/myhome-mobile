package com.example.myhome.features.meter.repositories

import com.example.myhome.features.meter.models.ReadingAddModel
import com.example.myhome.features.meter.models.ReadingListItemModel
import kotlinx.coroutines.flow.Flow

interface ReadingRepository {
    fun addReading(reading: ReadingAddModel): Flow<Boolean>
    fun listReading(meterId: Int): Flow<List<ReadingListItemModel>>
}