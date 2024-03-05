package com.example.myhome.features.meter.usecases

import com.example.myhome.features.meter.models.ReadingListItemModel
import kotlinx.coroutines.flow.Flow

interface ReadingListUseCase {
    operator fun invoke(meterId: Int): Flow<List<ReadingListItemModel>>
}
