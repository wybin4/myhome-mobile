package com.example.myhome.meter.storages

import com.example.myhome.meter.dtos.MeterAddDto
import com.example.myhome.meter.dtos.MeterGetDto
import com.example.myhome.meter.dtos.MeterListDto
import com.example.myhome.meter.dtos.MeterUpdateDto
import com.example.myhome.meter.services.MeterApiService

class MeterRemoteStorage(private val meterApiService: MeterApiService): MeterStorage {
    override suspend fun addMeter(meter: MeterAddDto): MeterGetDto {
        TODO("Not yet implemented")
    }

    override suspend fun updateMeter(meter: MeterUpdateDto): MeterGetDto {
        TODO("Not yet implemented")
    }

    override suspend fun listMeter(): List<MeterGetDto> {
        val request = MeterListDto(userId = 1)
        return meterApiService.listMeter(request)
    }
}