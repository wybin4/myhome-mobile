package com.example.myhome.data.meter.repositories

import com.example.myhome.data.meter.mappers.MeterRemoteMapper
import com.example.myhome.data.base.storages.MeterStorage
import com.example.myhome.domain.meter.models.MeterAddModel
import com.example.myhome.domain.meter.models.MeterGetModel
import com.example.myhome.domain.meter.models.MeterUpdateModel
import com.example.myhome.domain.meter.repositories.MeterRepository
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
