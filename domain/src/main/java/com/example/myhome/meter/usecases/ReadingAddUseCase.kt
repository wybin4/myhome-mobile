package com.example.myhome.meter.usecases

import com.example.myhome.meter.models.ReadingAddModel
import kotlinx.coroutines.flow.Flow

interface ReadingAddUseCase {
    operator fun invoke(reading: ReadingAddModel): Flow<Boolean>
}
