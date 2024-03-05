package com.example.myhome.features.meter.usecases

import com.example.myhome.features.meter.models.ReadingAddModel
import kotlinx.coroutines.flow.Flow

interface ReadingAddUseCase {
    operator fun invoke(reading: ReadingAddModel): Flow<Boolean>
}
