package com.example.myhome.features.meter.usecases

import com.example.myhome.features.meter.models.MeterListItemExtendedModel
import kotlinx.coroutines.flow.Flow

interface MeterListUseCase {
    operator fun invoke(): Flow<List<MeterListItemExtendedModel>>
}
