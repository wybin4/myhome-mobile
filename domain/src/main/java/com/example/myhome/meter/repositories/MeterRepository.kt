package com.example.myhome.meter.repositories

import com.example.myhome.meter.models.MeterAddModel
import com.example.myhome.meter.models.MeterGetModel
import com.example.myhome.meter.models.MeterUpdateModel
import kotlinx.coroutines.flow.Flow

interface MeterRepository {
    suspend fun addMeter(meter: MeterAddModel)
    suspend fun updateMeter(meter: MeterUpdateModel)
    fun listMeter(): Flow<List<MeterGetModel>>
}