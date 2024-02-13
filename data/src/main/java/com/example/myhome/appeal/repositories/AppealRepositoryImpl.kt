package com.example.myhome.appeal.repositories

import android.util.Log
import com.example.myhome.appeal.mappers.AppealRemoteMapper
import com.example.myhome.appeal.models.AppealAddMeterModel
import com.example.myhome.appeal.models.AppealUpdateMeterModel
import com.example.myhome.appeal.storages.AppealStorage

class AppealRepositoryImpl(
    private val appealStorage: AppealStorage,
    private val appealRemoteMapper: AppealRemoteMapper
): AppealRepository {
    override suspend fun addMeter(meter: AppealAddMeterModel) {
        val meterDto = appealRemoteMapper.mapAddToRemote(meter)
        appealStorage.addAppeal(meterDto)
    }

    override suspend fun updateMeter(meter: AppealUpdateMeterModel) {
        val meterDto = appealRemoteMapper.mapUpdateToRemote(meter)
        appealStorage.addAppeal(meterDto)
    }
}