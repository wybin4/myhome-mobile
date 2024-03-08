package com.example.myhome.features.appeal.usecases

import com.example.myhome.features.appeal.AppealRepository
import com.example.myhome.features.appeal.models.AppealAddMeterModel
import com.example.myhome.features.appeal.models.AppealProblemOrClaimModel
import com.example.myhome.features.appeal.models.AppealUpdateMeterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppealAddUseCase @Inject constructor(
    private val appealRepository: AppealRepository
) {
    fun addMeter(appeal: AppealAddMeterModel): Flow<Boolean> {
        return appealRepository.addMeter(appeal = appeal)
    }

    fun updateMeter(appeal: AppealUpdateMeterModel): Flow<Boolean> {
        return appealRepository.updateMeter(appeal = appeal)
    }

    fun problem(appeal: AppealProblemOrClaimModel): Flow<Boolean> {
        return appealRepository.problem(appeal = appeal)
    }

    fun claim(appeal: AppealProblemOrClaimModel): Flow<Boolean> {
        return appealRepository.claim(appeal = appeal)
    }
}