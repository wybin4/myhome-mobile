package com.example.myhome.data.base.storages

import com.example.myhome.data.meter.dtos.MeterAddDto
import com.example.myhome.data.meter.dtos.MeterGetDto
import com.example.myhome.data.meter.dtos.MeterUpdateDto

interface MeterStorage {
    suspend fun addMeter(meter: MeterAddDto): MeterGetDto
    suspend fun updateMeter(meter: MeterUpdateDto): MeterGetDto
    suspend fun listMeter(): List<MeterGetDto>
}