package com.example.myhome.data.meter.services

import com.example.myhome.data.meter.dtos.MeterAddDto
import com.example.myhome.data.meter.dtos.MeterGetDto
import com.example.myhome.data.meter.dtos.MeterListDto
import com.example.myhome.data.meter.dtos.MeterUpdateDto
import retrofit2.http.Body
import retrofit2.http.POST

interface MeterApiService {
    @POST("meter/add-meter")
    suspend fun addMeter(
        @Body request: MeterAddDto
    ): MeterGetDto
    @POST("meter/update-meter")
    suspend fun updateMeter(
        @Body request: MeterUpdateDto
    ): MeterGetDto

    @POST("meter/get-meters-by-user")
    suspend fun listMeter(
        @Body request: MeterListDto
    ): List<MeterGetDto>

//    suspend fun addReading(reading: ReadingAddDto): Flow<ReadingGetDto> {
//        TODO("Not yet implemented")
//    }
//     suspend fun listReading(meterId: Int): Flow<List<ReadingGetDto>> {
//        TODO("Not yet implemented")
//    }

}