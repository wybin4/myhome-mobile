package com.example.myhome.testutils.mocks.meter.repositories

import com.example.myhome.features.meter.dtos.ReadingAddRequest
import com.example.myhome.features.meter.dtos.ReadingListItemResponse
import com.example.myhome.features.meter.repositories.ReadingRepository
import com.example.myhome.testutils.MeterDomainTestListProvider.readingList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ReadingRepositoryTest : ReadingRepository {
    override fun addReading(reading: ReadingAddRequest): Flow<Boolean> {
        return flowOf(true)
    }

    override fun listReading(meterId: Int): Flow<List<ReadingListItemResponse>> {
        return flowOf(readingList)
    }
}
