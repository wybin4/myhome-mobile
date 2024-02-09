package com.example.myhome.data.storages

import com.example.myhome.data.dtos.MeterAddDto
import com.example.myhome.data.dtos.MeterGetDto
import com.example.myhome.data.dtos.MeterUpdateDto

interface MeterStorage {
    suspend fun addMeter(meter: MeterAddDto): MeterGetDto
    suspend fun updateMeter(meter: MeterUpdateDto): MeterGetDto
    suspend fun listMeter(): List<MeterGetDto>
}