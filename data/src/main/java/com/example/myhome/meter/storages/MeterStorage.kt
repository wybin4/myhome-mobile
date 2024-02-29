package com.example.myhome.meter.storages

import com.example.myhome.meter.dtos.ApartmentWithMeterGetDto
import com.example.myhome.meter.dtos.MeterListItemDto
import com.example.myhome.meter.dtos.ReadingAddDto
import com.example.myhome.meter.dtos.ReadingGetDto

interface MeterStorage {
    suspend fun listApartmentWithMeter(): List<ApartmentWithMeterGetDto>
    suspend fun listMeter(): List<MeterListItemDto>
    suspend fun listReading(meterId: Int): List<ReadingGetDto>
    suspend fun addReading(reading: ReadingAddDto)
}