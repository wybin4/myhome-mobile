package com.example.myhome.meter.services

import com.example.myhome.meter.dtos.ApartmentWithMeterGetDto
import com.example.myhome.meter.dtos.ApartmentWithMeterListDto
import com.example.myhome.meter.dtos.ReadingGetDto
import com.example.myhome.meter.dtos.ReadingListDto
import retrofit2.http.Body
import retrofit2.http.POST

interface MeterApiService {
    @POST("meter/get-meters-by-user")
    suspend fun listApartmentWithMeter(
        @Body request: ApartmentWithMeterListDto
    ): List<ApartmentWithMeterGetDto>

//    suspend fun addReading(reading: ReadingAddDto): Flow<ReadingGetDto> {
//        TODO("Not yet implemented")
//    }
    @POST("meter/get-meter-readings")
    suspend fun listReading(
        @Body request: ReadingListDto
    ): List<ReadingGetDto>
}