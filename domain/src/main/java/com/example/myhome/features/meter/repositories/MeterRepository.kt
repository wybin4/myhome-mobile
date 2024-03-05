package com.example.myhome.features.meter.repositories

import com.example.myhome.features.meter.models.ApartmentWithMeterListItemModel
import com.example.myhome.features.meter.models.MeterListItemExtendedModel
import kotlinx.coroutines.flow.Flow

interface MeterRepository {
    fun listApartmentWithMeter(): Flow<List<ApartmentWithMeterListItemModel>>
    fun listMeter(): Flow<List<MeterListItemExtendedModel>>
}