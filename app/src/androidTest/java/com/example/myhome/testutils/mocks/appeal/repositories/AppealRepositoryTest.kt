package com.example.myhome.testutils.mocks.appeal.repositories

import com.example.myhome.appeal.models.AppealAddMeterModel
import com.example.myhome.appeal.models.AppealUpdateMeterModel
import com.example.myhome.appeal.repositories.AppealRepository

class AppealRepositoryTest : AppealRepository {
    override suspend fun addMeter(appeal: AppealAddMeterModel) {}
    override suspend fun updateMeter(appeal: AppealUpdateMeterModel) {}
}
