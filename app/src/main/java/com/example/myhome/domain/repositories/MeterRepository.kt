package com.example.myhome.domain.repositories

import com.example.myhome.domain.models.MeterAddModel
import com.example.myhome.domain.models.MeterGetModel
import com.example.myhome.domain.models.MeterUpdateModel
import kotlinx.coroutines.flow.Flow

interface MeterRepository {
    fun addMeter(meter: MeterAddModel): Flow<MeterGetModel>
    fun updateMeter(meter: MeterUpdateModel): Flow<MeterGetModel>
    fun listMeter(): Flow<List<MeterGetModel>>
}