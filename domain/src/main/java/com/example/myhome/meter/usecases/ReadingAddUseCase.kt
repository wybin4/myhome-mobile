package com.example.myhome.meter.usecases

import com.example.myhome.meter.models.ReadingAddModel

interface ReadingAddUseCase {
    suspend operator fun invoke(reading: ReadingAddModel)
}
