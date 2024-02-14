package com.example.myhome.meter.repositories

import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import kotlinx.coroutines.flow.Flow

interface MeterRepository {
    fun listApartmentWithMeter(): Flow<List<ApartmentWithMeterGetModel>>
}