package com.example.myhome.features.meter.usecases

import com.example.myhome.features.meter.models.ReadingListItemModel
import com.example.myhome.features.meter.repositories.ReadingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadingListUseCase @Inject constructor(
    private val readingRepository: ReadingRepository
) {
    operator fun invoke(meterId: Int): Flow<List<ReadingListItemModel>> {
        return readingRepository.listReading(meterId)
    }
}
