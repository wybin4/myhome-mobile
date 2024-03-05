package com.example.myhome.features.meter.repositories

import com.example.myhome.features.meter.MeterStorage
import com.example.myhome.features.meter.mappers.ReadingRemoteMapper
import com.example.myhome.features.meter.models.ReadingAddModel
import com.example.myhome.features.meter.models.ReadingListItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReadingRepositoryImpl(
    private val meterStorage: MeterStorage,
    private val readingRemoteMapper: ReadingRemoteMapper
): ReadingRepository {
    override fun addReading(reading: ReadingAddModel): Flow<Boolean> = flow {
        val readingDto = readingRemoteMapper.mapAddToRemote(reading)
        emit(meterStorage.addReading(readingDto))
    }

    override fun listReading(meterId: Int): Flow<List<ReadingListItemModel>> = flow {
        val result = meterStorage.listReading(meterId)
        emit(readingRemoteMapper.mapListToDomain(result))
    }
}