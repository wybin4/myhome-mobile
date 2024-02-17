package com.example.myhome.meter.usecases

import com.example.myhome.meter.models.ReadingAddModel
import com.example.myhome.meter.repositories.ReadingRepository
import javax.inject.Inject

class ReadingAddUseCase @Inject constructor(
    private val readingRepository: ReadingRepository
) {
    suspend operator fun invoke(reading: ReadingAddModel) {
        readingRepository.addReading(reading)
    }
}
