package com.example.myhome.meter.usecases

import com.example.myhome.meter.models.ReadingAddModel

class ReadingAddUseCaseTest : ReadingAddUseCase {
    override suspend operator fun invoke(reading: ReadingAddModel) { }
}
