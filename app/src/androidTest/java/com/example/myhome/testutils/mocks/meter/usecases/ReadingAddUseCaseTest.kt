package com.example.myhome.testutils.mocks.meter.usecases

import com.example.myhome.meter.models.ReadingAddModel
import com.example.myhome.meter.usecases.ReadingAddUseCase

class ReadingAddUseCaseTest : ReadingAddUseCase {
    override suspend operator fun invoke(reading: ReadingAddModel) { }
}