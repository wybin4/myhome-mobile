package com.example.myhome.testutils.mocks.meter.repositories

import com.example.myhome.features.meter.models.ReadingAddModel
import com.example.myhome.features.meter.models.ReadingListItemModel
import com.example.myhome.features.meter.repositories.ReadingRepository
import com.example.myhome.testutils.providers.MeterDomainTestListProvider.readingList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ReadingRepositoryTest : ReadingRepository {
    override fun addReading(reading: ReadingAddModel): Flow<Boolean> {
        return flowOf(true)
    }

    override fun listReading(meterId: Int): Flow<List<ReadingListItemModel>> {
        return flowOf(readingList)
    }
}
