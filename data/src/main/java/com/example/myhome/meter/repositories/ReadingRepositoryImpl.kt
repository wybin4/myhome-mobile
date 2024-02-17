package com.example.myhome.meter.repositories

import com.example.myhome.meter.mappers.ReadingRemoteMapper
import com.example.myhome.meter.storages.MeterStorage
import com.example.myhome.meter.models.ReadingAddModel
import com.example.myhome.meter.models.ReadingGetModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReadingRepositoryImpl(
    private val meterStorage: MeterStorage,
    private val readingRemoteMapper: ReadingRemoteMapper
): ReadingRepository {
    override suspend fun addReading(reading: ReadingAddModel) {
        val readingDto = readingRemoteMapper.mapAddToRemote(reading)
        meterStorage.addReading(readingDto)
    }

    override fun listReading(meterId: Int): Flow<List<ReadingGetModel>> = flow {
        val result = meterStorage.listReading(meterId)
        emit(readingRemoteMapper.mapListToDomain(result))
    }
}