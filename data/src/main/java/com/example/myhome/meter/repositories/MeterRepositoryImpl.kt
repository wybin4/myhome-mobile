package com.example.myhome.meter.repositories

import com.example.myhome.meter.mappers.MeterRemoteMapper
import com.example.myhome.meter.storages.MeterStorage
import com.example.myhome.meter.models.MeterGetModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MeterRepositoryImpl(
    private val meterStorage: MeterStorage,
    private val meterRemoteMapper: MeterRemoteMapper
): MeterRepository {
    override fun listMeter(): Flow<List<MeterGetModel>> = flow {
        val result = meterStorage.listMeter()
        emit(meterRemoteMapper.mapListToDomain(result))
    }
}
