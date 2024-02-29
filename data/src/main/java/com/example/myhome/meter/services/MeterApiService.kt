package com.example.myhome.meter.services

import com.example.myhome.meter.dtos.ApartmentWithMeterGetDto
import com.example.myhome.meter.dtos.ApartmentWithMeterListDto
import com.example.myhome.meter.dtos.MeterListDto
import com.example.myhome.meter.dtos.MeterListItemDto
import com.example.myhome.meter.dtos.ReadingAddDto
import com.example.myhome.meter.dtos.ReadingGetDto
import com.example.myhome.meter.dtos.ReadingListDto
import retrofit2.http.Body
import retrofit2.http.POST

interface MeterApiService {
    @POST("meter/get-meters-by-user")
    suspend fun listApartmentWithMeter(
        @Body request: ApartmentWithMeterListDto
    ): List<ApartmentWithMeterGetDto>

    @POST("meter/get-meters-by-user")
    suspend fun listMeter(
        @Body request: MeterListDto
    ): List<MeterListItemDto>

    @POST("meter/get-meter-readings")
    suspend fun listReading(
        @Body request: ReadingListDto
    ): List<ReadingGetDto>

    @POST("meter/add-meter-reading")
    suspend fun addReading(
        @Body request: ReadingAddDto
    )
}