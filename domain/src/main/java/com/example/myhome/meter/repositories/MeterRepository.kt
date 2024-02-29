package com.example.myhome.meter.repositories

import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.meter.models.MeterListItemModel
import kotlinx.coroutines.flow.Flow

interface MeterRepository {
    fun listApartmentWithMeter(): Flow<List<ApartmentWithMeterGetModel>>
    fun listMeter(): Flow<List<MeterListItemModel>>
}