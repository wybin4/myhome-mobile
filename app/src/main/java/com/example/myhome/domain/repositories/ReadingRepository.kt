package com.example.myhome.domain.repositories

import com.example.myhome.domain.models.ReadingAddModel
import com.example.myhome.domain.models.ReadingGetModel
import kotlinx.coroutines.flow.Flow

interface ReadingRepository {
    fun addReading(reading: ReadingAddModel): Flow<ReadingGetModel>
    fun listReading(meterId: Int): Flow<List<ReadingGetModel>>
}