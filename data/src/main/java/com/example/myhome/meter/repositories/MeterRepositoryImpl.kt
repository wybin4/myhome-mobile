package com.example.myhome.meter.repositories

import com.example.myhome.meter.mappers.MeterRemoteMapper
import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.meter.models.MeterListItemModel
import com.example.myhome.meter.storages.MeterStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MeterRepositoryImpl(
    private val meterStorage: MeterStorage,
    private val meterRemoteMapper: MeterRemoteMapper
): MeterRepository {
    override fun listApartmentWithMeter(): Flow<List<ApartmentWithMeterGetModel>> = flow {
        val result = meterStorage.listApartmentWithMeter()
        emit(meterRemoteMapper.mapApartmentWithMeterListToDomain(result))
    }

    override fun listMeter(): Flow<List<MeterListItemModel>> = flow {
        val result = meterStorage.listMeter()
        emit(meterRemoteMapper.mapMeterListToDomain(result))
    }

}
