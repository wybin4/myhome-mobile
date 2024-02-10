package com.example.myhome.meter.repositories

import com.example.myhome.meter.mappers.ReadingRemoteMapper
import com.example.myhome.meter.storages.MeterStorage
import com.example.myhome.meter.models.ReadingAddModel
import com.example.myhome.meter.models.ReadingGetModel
import kotlinx.coroutines.flow.Flow

class ReadingRepositoryImpl(
    private val meterStorage: MeterStorage,
    private val readingRemoteMapper: ReadingRemoteMapper
): ReadingRepository {
    override fun addReading(reading: ReadingAddModel): Flow<ReadingGetModel> {
//        val meterDto = readingRemoteMapper.mapAddToRemote(reading)
//        val result = meterStorage.addReading(meterDto)
//        return result.map { readingRemoteMapper.mapToDomain(it) }
        TODO("Not yet implemented")
    }

    override fun listReading(meterId: Int): Flow<List<ReadingGetModel>> {
//        val result = meterStorage.listReading(meterId)
//        return result.map { meters ->
//            readingRemoteMapper.mapListToDomain(meters)
//        }
        TODO("Not yet implemented")
    }
}