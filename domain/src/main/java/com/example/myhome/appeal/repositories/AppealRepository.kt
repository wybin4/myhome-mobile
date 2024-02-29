package com.example.myhome.appeal.repositories

import com.example.myhome.appeal.models.AppealAddMeterModel
import com.example.myhome.appeal.models.AppealGetModel
import com.example.myhome.appeal.models.AppealProblemOrClaimModel
import com.example.myhome.appeal.models.AppealUpdateMeterModel
import kotlinx.coroutines.flow.Flow

interface AppealRepository {
    fun addMeter(appeal: AppealAddMeterModel): Flow<Boolean>
    fun updateMeter(appeal: AppealUpdateMeterModel): Flow<Boolean>
    fun problem(appeal: AppealProblemOrClaimModel): Flow<Boolean>
    fun claim(appeal: AppealProblemOrClaimModel): Flow<Boolean>
    fun listAppeal(): Flow<List<AppealGetModel>>
}