package com.example.myhome.appeal.repositories

import com.example.myhome.appeal.mappers.AppealRemoteMapper
import com.example.myhome.appeal.models.AppealAddMeterModel
import com.example.myhome.appeal.models.AppealGetModel
import com.example.myhome.appeal.models.AppealProblemOrClaimModel
import com.example.myhome.appeal.models.AppealUpdateMeterModel
import com.example.myhome.appeal.storages.AppealStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class AppealRepositoryImpl(
    private val appealStorage: AppealStorage,
    private val appealRemoteMapper: AppealRemoteMapper
): AppealRepository {
    override fun addMeter(meter: AppealAddMeterModel): Flow<Boolean> = flow {
        val meterDto = appealRemoteMapper.mapAddToRemote(meter)
        emit(appealStorage.addAppeal(meterDto))
    }

    override fun updateMeter(meter: AppealUpdateMeterModel): Flow<Boolean> = flow {
        val meterDto = appealRemoteMapper.mapUpdateToRemote(meter)
        emit(appealStorage.addAppeal(meterDto))
    }

    override fun problem(appeal: AppealProblemOrClaimModel): Flow<Boolean> = flow {
        val meterDto = appealRemoteMapper.mapProblemToRemote(appeal)
        emit(appealStorage.addAppeal(meterDto))
    }

    override fun claim(appeal: AppealProblemOrClaimModel): Flow<Boolean> = flow {
        val meterDto = appealRemoteMapper.mapClaimToRemote(appeal)
        emit(appealStorage.addAppeal(meterDto))
    }

    override fun listAppeal(): Flow<List<AppealGetModel>> = flow {
        val result = appealStorage.listAppeal()
        emit(appealRemoteMapper.mapListToDomain(result))
    }

}