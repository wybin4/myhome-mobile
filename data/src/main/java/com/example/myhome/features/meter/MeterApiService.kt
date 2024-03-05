package com.example.myhome.features.meter

import com.example.myhome.features.meter.dtos.ApartmentWithMeterListRequest
import com.example.myhome.features.meter.dtos.ApartmentWithMeterListItemResponse
import com.example.myhome.features.meter.dtos.MeterListRequestExtended
import com.example.myhome.features.meter.dtos.MeterListItemResponseExtended
import com.example.myhome.features.meter.dtos.ReadingAddRequest
import com.example.myhome.features.meter.dtos.ReadingListRequest
import com.example.myhome.features.meter.dtos.ReadingListItemResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MeterApiService {
    @POST("meter/get-meters-by-user")
    suspend fun listApartmentWithMeter(
        @Body request: ApartmentWithMeterListRequest
    ): List<ApartmentWithMeterListItemResponse>

    @POST("meter/get-meters-by-user")
    suspend fun listMeter(
        @Body request: MeterListRequestExtended
    ): List<MeterListItemResponseExtended>

    @POST("meter/get-meter-readings")
    suspend fun listReading(
        @Body request: ReadingListRequest
    ): List<ReadingListItemResponse>

    @POST("meter/add-meter-reading")
    suspend fun addReading(
        @Body request: ReadingAddRequest
    ): ReadingListItemResponse
}