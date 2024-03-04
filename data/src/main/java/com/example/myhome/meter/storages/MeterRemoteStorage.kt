package com.example.myhome.meter.storages


import com.example.myhome.meter.dtos.ApartmentWithMeterGetDto
import com.example.myhome.meter.dtos.ApartmentWithMeterListDto
import com.example.myhome.meter.dtos.MeterListDto
import com.example.myhome.meter.dtos.MeterListItemDto
import com.example.myhome.meter.dtos.ReadingAddDto
import com.example.myhome.meter.dtos.ReadingGetDto
import com.example.myhome.meter.dtos.ReadingListDto
import com.example.myhome.meter.services.MeterApiService

class MeterRemoteStorage(private val meterApiService: MeterApiService): MeterStorage {
    override suspend fun listApartmentWithMeter(): List<ApartmentWithMeterGetDto> {
        val request = ApartmentWithMeterListDto(userId = 1)
        return meterApiService.listApartmentWithMeter(request)
    }

    override suspend fun listMeter(): List<MeterListItemDto> {
        val request = MeterListDto(userId = 1)
        return meterApiService.listMeter(request)
    }

    override suspend fun listReading(meterId: Int): List<ReadingGetDto> {
        val request = ReadingListDto(meterId)
        return meterApiService.listReading(request)
    }

    override suspend fun addReading(reading: ReadingAddDto): Boolean {
        val result = meterApiService.addReading(reading)
        if (result != null) {
            return true
        }
        return false
    }
}