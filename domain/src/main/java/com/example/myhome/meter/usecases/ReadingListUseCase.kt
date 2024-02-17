package com.example.myhome.meter.usecases

import com.example.myhome.meter.models.ReadingGetModel
import com.example.myhome.meter.repositories.ReadingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.Date
import javax.inject.Inject

class ReadingListUseCase @Inject constructor(
    private val readingRepository: ReadingRepository
) {
    operator fun invoke(meterId: Int): Flow<List<ReadingGetModel>> {
        return readingRepository.listReading(meterId)
    }
}
