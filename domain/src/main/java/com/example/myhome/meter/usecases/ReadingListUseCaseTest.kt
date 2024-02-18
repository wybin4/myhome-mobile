package com.example.myhome.meter.usecases

import com.example.myhome.meter.models.ReadingGetModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.Date

class ReadingListUseCaseTest : ReadingListUseCase {
    val date = Date()

    override operator fun invoke(meterId: Int): Flow<List<ReadingGetModel>> {
        return flowOf(listOf(
            ReadingGetModel(
                id = 1,
                readAt = date,
                consumption = 1.23,
                reading = 5.43
            ),
            ReadingGetModel(
                id = 2,
                readAt = date,
                consumption = 0.23,
                reading = 1.26
            )
        ))
    }
}
