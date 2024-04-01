package com.example.myhome.features.meter

import com.example.myhome.features.meter.dtos.ApartmentWithMeterListRequest
import com.example.myhome.features.meter.dtos.ApartmentWithMeterListResponse
import com.example.myhome.features.meter.dtos.MeterExtendedListItemResponse
import com.example.myhome.features.meter.dtos.MeterListRequestExtended
import com.example.myhome.features.meter.dtos.ReadingAddRequest
import com.example.myhome.features.meter.dtos.ReadingListItemResponse
import com.example.myhome.features.meter.dtos.ReadingListRequest
import com.example.myhome.features.meter.dtos.ReadingListResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface MeterApiService {
    @POST("meter/get-meters-by-user")
    suspend fun listApartmentWithMeter(
        @Body request: ApartmentWithMeterListRequest
    ): ApartmentWithMeterListResponse

    @POST("meter/get-meters-by-user")
    suspend fun listMeter(
        @Body request: MeterListRequestExtended
    ): List<MeterExtendedListItemResponse>

    @POST("meter/get-meter-readings-by-user")
    suspend fun listReading(
        @Body request: ReadingListRequest
    ): ReadingListResponse

    @POST("meter/add-meter-reading")
    suspend fun addReading(
        @Body request: ReadingAddRequest
    ): ReadingListItemResponse
}