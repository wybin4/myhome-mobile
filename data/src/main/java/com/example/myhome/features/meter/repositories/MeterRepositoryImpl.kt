package com.example.myhome.features.meter.repositories

import com.example.myhome.features.meter.MeterApiService
import com.example.myhome.features.meter.dtos.ApartmentWithMeterListItemResponse
import com.example.myhome.features.meter.dtos.ApartmentWithMeterListRequest
import com.example.myhome.features.meter.dtos.MeterExtendedListItemResponse
import com.example.myhome.features.meter.dtos.MeterListRequestExtended
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MeterRepositoryImpl(
    private val meterApiService: MeterApiService
): MeterRepository {
    override fun listApartmentWithMeter(): Flow<List<ApartmentWithMeterListItemResponse>> = flow {
        val request = ApartmentWithMeterListRequest()
        val response = meterApiService.listApartmentWithMeter(request)
        emit(response.meters)
    }

    override fun listMeter(): Flow<List<MeterExtendedListItemResponse>> = flow {
        val request = MeterListRequestExtended()
        val response = meterApiService.listMeter(request)
        emit(response.meters)
    }

}
