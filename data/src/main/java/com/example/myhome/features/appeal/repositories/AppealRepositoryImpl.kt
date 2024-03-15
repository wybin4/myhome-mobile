package com.example.myhome.features.appeal.repositories

import com.example.myhome.features.appeal.AppealAddMeterAddRequest
import com.example.myhome.features.appeal.AppealListItemResponse
import com.example.myhome.features.appeal.AppealClaimAddRequest
import com.example.myhome.features.appeal.AppealProblemAddRequest
import com.example.myhome.features.appeal.AppealStorage
import com.example.myhome.features.appeal.AppealVerifyMeterAddRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppealRepositoryImpl(
    private val appealStorage: AppealStorage
): AppealRepository {
    override fun addMeter(appeal: AppealAddMeterAddRequest): Flow<Boolean> = flow {
        emit(appealStorage.addAppeal(appeal))
    }

    override fun updateMeter(appeal: AppealVerifyMeterAddRequest): Flow<Boolean> = flow {
        emit(appealStorage.addAppeal(appeal))
    }

    override fun problem(appeal: AppealProblemAddRequest): Flow<Boolean> = flow {
        emit(appealStorage.addAppeal(appeal))
    }

    override fun claim(appeal: AppealClaimAddRequest): Flow<Boolean> = flow {
        emit(appealStorage.addAppeal(appeal))
    }

    override fun listAppeal(): Flow<List<AppealListItemResponse>> = flow {
        emit(appealStorage.listAppeal())
    }

}