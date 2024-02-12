package com.example.myhome.meter.services

import com.example.myhome.meter.dtos.MeterGetDto
import com.example.myhome.meter.dtos.MeterListDto
import retrofit2.http.Body
import retrofit2.http.POST

interface MeterApiService {
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