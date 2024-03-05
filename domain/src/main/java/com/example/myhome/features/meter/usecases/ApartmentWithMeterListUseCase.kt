package com.example.myhome.features.meter.usecases

import com.example.myhome.features.meter.models.ApartmentWithMeterListItemModel
import kotlinx.coroutines.flow.Flow

interface ApartmentWithMeterListUseCase {
    operator fun invoke(): Flow<List<ApartmentWithMeterListItemModel>>
}
