package com.example.myhome.meter.usecases

import com.example.myhome.meter.models.ReadingAddModel
import com.example.myhome.meter.repositories.ReadingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadingAddUseCaseImpl @Inject constructor(
    private val readingRepository: ReadingRepository
) : ReadingAddUseCase {
    override operator fun invoke(reading: ReadingAddModel): Flow<Boolean> {
        return readingRepository.addReading(reading)
    }
}
