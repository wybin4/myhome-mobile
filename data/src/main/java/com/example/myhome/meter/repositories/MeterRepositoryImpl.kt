package com.example.myhome.meter.repositories

import com.example.myhome.meter.mappers.MeterRemoteMapper
import com.example.myhome.meter.storages.MeterStorage
import com.example.myhome.meter.models.MeterAddModel
import com.example.myhome.meter.models.MeterGetModel
import com.example.myhome.meter.models.MeterUpdateModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MeterRepositoryImpl(
    private val meterStorage: MeterStorage,
    private val meterRemoteMapper: MeterRemoteMapper
): MeterRepository {
    override fun addMeter(meter: MeterAddModel): Flow<MeterGetModel> = flow {
        val meterDto = meterRemoteMapper.mapAddToRemote(meter)
        val result = meterStorage.addMeter(meterDto)
        emit(meterRemoteMapper.mapToDomain(result))
    }

    override fun updateMeter(meter: MeterUpdateModel): Flow<MeterGetModel> = flow {
        val meterDto = meterRemoteMapper.mapUpdateToRemote(meter)
        val result = meterStorage.updateMeter(meterDto)
        emit(meterRemoteMapper.mapToDomain(result))
    }

    override fun listMeter(): Flow<List<MeterGetModel>> = flow {
        val result = meterStorage.listMeter()
        emit(meterRemoteMapper.mapListToDomain(result))
    }
}
