package com.example.myhome.meter.usecases

import com.example.myhome.meter.models.ReadingGetModel
import kotlinx.coroutines.flow.Flow

interface ReadingListUseCase {
    operator fun invoke(meterId: Int): Flow<List<ReadingGetModel>>
}
