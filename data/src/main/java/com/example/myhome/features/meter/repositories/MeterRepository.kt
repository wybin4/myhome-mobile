package com.example.myhome.features.meter.repositories

import com.example.myhome.features.meter.dtos.ApartmentWithMeterListItemResponse
import com.example.myhome.features.meter.dtos.MeterExtendedListItemResponse
import kotlinx.coroutines.flow.Flow

interface MeterRepository {
    fun listApartmentWithMeter(): Flow<List<ApartmentWithMeterListItemResponse>>
    fun listMeter(): Flow<List<MeterExtendedListItemResponse>>
}