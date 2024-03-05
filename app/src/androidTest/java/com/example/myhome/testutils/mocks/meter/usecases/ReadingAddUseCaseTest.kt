package com.example.myhome.testutils.mocks.meter.usecases

import com.example.myhome.features.meter.models.ReadingAddModel
import com.example.myhome.features.meter.usecases.ReadingAddUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ReadingAddUseCaseTest : ReadingAddUseCase {
    override operator fun invoke(reading: ReadingAddModel): Flow<Boolean> {
        return flowOf(true)
    }
}
