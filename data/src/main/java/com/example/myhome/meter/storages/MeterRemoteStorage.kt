package com.example.myhome.meter.storages

import com.example.myhome.meter.dtos.MeterGetDto
import com.example.myhome.meter.dtos.MeterListDto
import com.example.myhome.meter.dtos.ReadingGetDto
import com.example.myhome.meter.dtos.ReadingListDto
import com.example.myhome.meter.services.MeterApiService

class MeterRemoteStorage(private val meterApiService: MeterApiService): MeterStorage {
    override suspend fun listMeter(): List<MeterGetDto> {
        val request = MeterListDto(userId = 1)
        return meterApiService.listMeter(request)
    }
    override suspend fun listReading(meterId: Int): List<ReadingGetDto> {
        val request = ReadingListDto(meterId)
        return meterApiService.listReading(request)
    }
}