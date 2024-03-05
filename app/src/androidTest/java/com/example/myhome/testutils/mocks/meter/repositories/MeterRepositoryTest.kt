package com.example.myhome.testutils.mocks.meter.repositories

import com.example.myhome.features.meter.models.ApartmentWithMeterListItemModel
import com.example.myhome.features.meter.models.MeterListItemExtendedModel
import com.example.myhome.features.meter.repositories.MeterRepository
import com.example.myhome.presentation.testutils.providers.MeterUITestListProvider.getApartmentWithMeterList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class MeterRepositoryTest : MeterRepository {
    override fun listMeter(): Flow<List<MeterListItemExtendedModel>> {
        TODO("Not yet implemented")
    }
    
    override fun listApartmentWithMeter(): Flow<List<ApartmentWithMeterListItemModel>> {
        return flowOf(getApartmentWithMeterList())
    }

}
