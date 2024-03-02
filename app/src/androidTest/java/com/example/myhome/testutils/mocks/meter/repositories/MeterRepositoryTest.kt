package com.example.myhome.testutils.mocks.meter.repositories

import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.meter.models.MeterListItemModel
import com.example.myhome.meter.repositories.MeterRepository
import com.example.myhome.testutils.providers.MeterUITestListProvider.getApartmentWithMeterList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MeterRepositoryTest : MeterRepository {
    override fun listMeter(): Flow<List<MeterListItemModel>> {
        TODO("Not yet implemented")
    }
    
    override fun listApartmentWithMeter(): Flow<List<ApartmentWithMeterGetModel>> {
        return flowOf(getApartmentWithMeterList())
    }

}
