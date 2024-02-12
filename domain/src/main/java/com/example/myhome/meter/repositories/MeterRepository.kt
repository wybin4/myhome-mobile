package com.example.myhome.meter.repositories

import com.example.myhome.meter.models.MeterGetModel
import kotlinx.coroutines.flow.Flow

interface MeterRepository {
    fun listMeter(): Flow<List<MeterGetModel>>
}