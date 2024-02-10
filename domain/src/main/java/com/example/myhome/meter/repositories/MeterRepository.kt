package com.example.myhome.meter.repositories

import com.example.myhome.meter.models.MeterAddModel
import com.example.myhome.meter.models.MeterGetModel
import com.example.myhome.meter.models.MeterUpdateModel
import kotlinx.coroutines.flow.Flow

interface MeterRepository {
    fun addMeter(meter: MeterAddModel): Flow<MeterGetModel>
    fun updateMeter(meter: MeterUpdateModel): Flow<MeterGetModel>
    fun listMeter(): Flow<List<MeterGetModel>>
}