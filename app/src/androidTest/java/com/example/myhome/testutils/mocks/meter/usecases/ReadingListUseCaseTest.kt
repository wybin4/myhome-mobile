package com.example.myhome.testutils.mocks.meter.usecases

import com.example.myhome.features.meter.models.ReadingListItemModel
import com.example.myhome.features.meter.usecases.ReadingListUseCase
import com.example.myhome.testutils.providers.MeterDomainTestListProvider.readingList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ReadingListUseCaseTest : ReadingListUseCase {
    override operator fun invoke(meterId: Int): Flow<List<ReadingListItemModel>> {
        return flowOf(readingList)
    }
}
