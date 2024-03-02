package com.example.myhome.testutils.mocks.appeal.usecases

import com.example.myhome.appeal.models.AppealAddMeterModel
import com.example.myhome.appeal.models.AppealProblemOrClaimModel
import com.example.myhome.appeal.models.AppealUpdateMeterModel
import com.example.myhome.appeal.usecases.AppealAddUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AppealAddUseCaseTest : AppealAddUseCase {
    override fun addMeter(appeal: AppealAddMeterModel): Flow<Boolean> {
        return flowOf(true)
    }

    override fun updateMeter(appeal: AppealUpdateMeterModel): Flow<Boolean> {
        return flowOf(true)
    }

    override fun problem(appeal: AppealProblemOrClaimModel): Flow<Boolean> {
        return flowOf(true)
    }

    override fun claim(appeal: AppealProblemOrClaimModel): Flow<Boolean> {
        return flowOf(true)
    }
}
