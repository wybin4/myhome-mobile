package com.example.myhome.meter.storages

import com.example.myhome.meter.dtos.MeterAddDto
import com.example.myhome.meter.dtos.MeterGetDto
import com.example.myhome.meter.dtos.MeterUpdateDto

interface MeterStorage {
    suspend fun addMeter(meter: MeterAddDto): MeterGetDto
    suspend fun updateMeter(meter: MeterUpdateDto): MeterGetDto
    suspend fun listMeter(): List<MeterGetDto>
}