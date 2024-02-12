package com.example.myhome.appeal.repositories

import com.example.myhome.appeal.models.AppealAddMeterModel
import com.example.myhome.appeal.models.AppealUpdateMeterModel

interface AppealRepository {
    suspend fun addMeter(appeal: AppealAddMeterModel)
    suspend fun updateMeter(appeal: AppealUpdateMeterModel)
}