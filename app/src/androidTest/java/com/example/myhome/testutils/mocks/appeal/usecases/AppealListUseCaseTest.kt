package com.example.myhome.testutils.mocks.appeal.usecases

import com.example.myhome.appeal.models.AppealAddMeterModel
import com.example.myhome.appeal.models.AppealUpdateMeterModel
import com.example.myhome.appeal.usecases.AppealAddUseCase

class AppealAddUseCaseTest : AppealAddUseCase {
    override suspend fun addMeter(appeal: AppealAddMeterModel) {}
    override suspend fun updateMeter(appeal: AppealUpdateMeterModel) {}
}
