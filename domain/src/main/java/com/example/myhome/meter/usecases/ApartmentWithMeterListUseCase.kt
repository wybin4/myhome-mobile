package com.example.myhome.meter.usecases

import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import kotlinx.coroutines.flow.Flow

interface ApartmentWithMeterListUseCase {
    operator fun invoke(): Flow<List<ApartmentWithMeterGetModel>>
}
