package com.example.myhome.features.meter.repositories

import com.example.myhome.features.meter.MeterStorage
import com.example.myhome.features.meter.mappers.MeterRemoteMapper
import com.example.myhome.features.meter.models.ApartmentWithMeterListItemModel
import com.example.myhome.features.meter.models.MeterListItemExtendedModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MeterRepositoryImpl(
    private val meterStorage: MeterStorage,
    private val meterRemoteMapper: MeterRemoteMapper
): MeterRepository {
    override fun listApartmentWithMeter(): Flow<List<ApartmentWithMeterListItemModel>> = flow {
        val result = meterStorage.listApartmentWithMeter()
        emit(meterRemoteMapper.mapApartmentWithMeterListToDomain(result))
    }

    override fun listMeter(): Flow<List<MeterListItemExtendedModel>> = flow {
        val result = meterStorage.listMeter()
        emit(meterRemoteMapper.mapMeterListToDomain(result))
    }

}
