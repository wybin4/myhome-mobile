package com.example.myhome.meter.usecases

import com.example.myhome.meter.models.MeterListItemModel
import kotlinx.coroutines.flow.Flow

interface MeterListUseCase {
    operator fun invoke(): Flow<List<MeterListItemModel>>
}
