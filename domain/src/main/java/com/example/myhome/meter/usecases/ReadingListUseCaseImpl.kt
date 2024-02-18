package com.example.myhome.meter.usecases

import com.example.myhome.meter.models.ReadingGetModel
import com.example.myhome.meter.repositories.ReadingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadingListUseCaseImpl @Inject constructor(
    private val readingRepository: ReadingRepository
): ReadingListUseCase {
    override operator fun invoke(meterId: Int): Flow<List<ReadingGetModel>> {
        return readingRepository.listReading(meterId)
    }
}
