package com.example.myhome.testutils.mocks.meter.usecases

import com.example.myhome.meter.models.ReadingAddModel
import com.example.myhome.meter.usecases.ReadingAddUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ReadingAddUseCaseTest : ReadingAddUseCase {
    override operator fun invoke(reading: ReadingAddModel): Flow<Boolean> {
        return flowOf(true)
    }
}
