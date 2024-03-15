package com.example.myhome.features.meter.repositories

import com.example.myhome.features.meter.MeterApiService
import com.example.myhome.features.meter.dtos.ReadingAddRequest
import com.example.myhome.features.meter.dtos.ReadingListItemResponse
import com.example.myhome.features.meter.dtos.ReadingListRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ReadingRepositoryImpl(
    private val meterApiService: MeterApiService
): ReadingRepository {
    override fun addReading(reading: ReadingAddRequest): Flow<Boolean> = flow {
        val result = meterApiService.addReading(reading)
        if (result != null) {
            emit(true)
        }
        emit(false)
    }

    override fun listReading(meterId: Int): Flow<List<ReadingListItemResponse>> = flow {
        val request = ReadingListRequest(meterId)
        emit(meterApiService.listReading(request))
    }
}