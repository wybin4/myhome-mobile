package com.example.myhome.meter.storages

import com.example.myhome.meter.dtos.MeterGetDto
import com.example.myhome.meter.dtos.ReadingGetDto

interface MeterStorage {
    suspend fun listMeter(): List<MeterGetDto>
    suspend fun listReading(meterId: Int): List<ReadingGetDto>
}