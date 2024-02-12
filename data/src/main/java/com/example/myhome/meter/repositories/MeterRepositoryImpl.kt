package com.example.myhome.meter.repositories

import com.example.myhome.appeal.storages.AppealStorage
import com.example.myhome.meter.mappers.MeterRemoteMapper
import com.example.myhome.meter.storages.MeterStorage
import com.example.myhome.meter.models.MeterAddModel
import com.example.myhome.meter.models.MeterGetModel
import com.example.myhome.meter.models.MeterUpdateModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MeterRepositoryImpl(
    private val meterStorage: MeterStorage,
    private val appealStorage: AppealStorage,
    private val meterRemoteMapper: MeterRemoteMapper
): MeterRepository {
    override suspend fun addMeter(meter: MeterAddModel) {
        val meterDto = meterRemoteMapper.mapAddToRemote(meter)
        appealStorage.addAppeal(meterDto)
    }

    override suspend fun updateMeter(meter: MeterUpdateModel) {
        val meterDto = meterRemoteMapper.mapUpdateToRemote(meter)
        appealStorage.addAppeal(meterDto)
    }

    override fun listMeter(): Flow<List<MeterGetModel>> = flow {
        val result = meterStorage.listMeter()
        emit(meterRemoteMapper.mapListToDomain(result))
    }
}
