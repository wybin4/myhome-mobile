package com.example.myhome.features.meter

import com.example.myhome.features.meter.dtos.ApartmentWithMeterListRequest
import com.example.myhome.features.meter.dtos.ApartmentWithMeterListItemResponse
import com.example.myhome.features.meter.dtos.MeterListRequestExtended
import com.example.myhome.features.meter.dtos.MeterListItemResponseExtended
import com.example.myhome.features.meter.dtos.ReadingAddRequest
import com.example.myhome.features.meter.dtos.ReadingListRequest
import com.example.myhome.features.meter.dtos.ReadingListItemResponse

class MeterStorage(private val meterApiService: MeterApiService) {
    suspend fun listApartmentWithMeter(): List<ApartmentWithMeterListItemResponse> {
        val request = ApartmentWithMeterListRequest(userId = 1)
        return meterApiService.listApartmentWithMeter(request)
    }

    suspend fun listMeter(): List<MeterListItemResponseExtended> {
        val request = MeterListRequestExtended(userId = 1)
        return meterApiService.listMeter(request)
    }

    suspend fun listReading(meterId: Int): List<ReadingListItemResponse> {
        val request = ReadingListRequest(meterId)
        return meterApiService.listReading(request)
    }

    suspend fun addReading(reading: ReadingAddRequest): Boolean {
        val result = meterApiService.addReading(reading)
        if (result != null) {
            return true
        }
        return false
    }
}