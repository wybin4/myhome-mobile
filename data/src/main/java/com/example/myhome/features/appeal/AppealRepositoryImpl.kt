package com.example.myhome.features.appeal

import com.example.myhome.features.appeal.models.AppealAddMeterModel
import com.example.myhome.features.appeal.models.AppealListItemModel
import com.example.myhome.features.appeal.models.AppealProblemOrClaimModel
import com.example.myhome.features.appeal.models.AppealUpdateMeterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

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

    override fun listAppeal(): Flow<List<AppealListItemModel>> = flow {
        val result = appealStorage.listAppeal()
        emit(appealRemoteMapper.mapListToDomain(result))
    }

}