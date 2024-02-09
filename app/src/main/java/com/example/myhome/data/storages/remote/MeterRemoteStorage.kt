package com.example.myhome.data.storages.remote

import com.example.myhome.data.dtos.MeterAddDto
import com.example.myhome.data.dtos.MeterGetDto
import com.example.myhome.data.dtos.MeterListDto
import com.example.myhome.data.dtos.MeterUpdateDto
import com.example.myhome.data.services.MeterApiService
import com.example.myhome.data.storages.MeterStorage
import kotlinx.coroutines.flow.Flow

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