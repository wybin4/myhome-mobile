package com.example.myhome.data.repositories

import com.example.myhome.data.mappers.remote.MeterRemoteMapper
import com.example.myhome.data.storages.MeterStorage
import com.example.myhome.domain.models.MeterAddModel
import com.example.myhome.domain.models.MeterGetModel
import com.example.myhome.domain.models.MeterUpdateModel
import com.example.myhome.domain.repositories.MeterRepository
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
