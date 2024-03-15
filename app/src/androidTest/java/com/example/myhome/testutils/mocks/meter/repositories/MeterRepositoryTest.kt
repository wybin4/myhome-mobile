package com.example.myhome.testutils.mocks.meter.repositories

import com.example.myhome.features.meter.dtos.ApartmentWithMeterListItemResponse
import com.example.myhome.features.meter.dtos.MeterExtendedListItemResponse
import com.example.myhome.features.meter.repositories.MeterRepository
import com.example.myhome.presentation.testutils.providers.MeterUITestListProvider.getApartmentWithMeterList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MeterRepositoryTest : MeterRepository {
    override fun listMeter(): Flow<List<MeterExtendedListItemResponse>> {
        TODO("Not yet implemented")
    }
    
    override fun listApartmentWithMeter(): Flow<List<ApartmentWithMeterListItemResponse>> {
        return flowOf(getApartmentWithMeterList())
    }

}
