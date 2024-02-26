package com.example.myhome.appeal.usecases

import com.example.myhome.appeal.models.AppealAddMeterModel
import com.example.myhome.appeal.models.AppealUpdateMeterModel

interface AppealAddUseCase {
    suspend fun addMeter(appeal: AppealAddMeterModel)
    suspend fun updateMeter(appeal: AppealUpdateMeterModel)
}