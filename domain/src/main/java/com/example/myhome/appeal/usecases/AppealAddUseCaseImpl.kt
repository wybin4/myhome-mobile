package com.example.myhome.appeal.usecases

import com.example.myhome.appeal.models.AppealAddMeterModel
import com.example.myhome.appeal.models.AppealProblemOrClaimModel
import com.example.myhome.appeal.models.AppealUpdateMeterModel
import com.example.myhome.appeal.repositories.AppealRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppealAddUseCaseImpl @Inject constructor(
    private val appealRepository: AppealRepository
) : AppealAddUseCase {
    override fun addMeter(appeal: AppealAddMeterModel): Flow<Boolean> {
        return appealRepository.addMeter(appeal = appeal)
    }

    override fun updateMeter(appeal: AppealUpdateMeterModel): Flow<Boolean> {
        return appealRepository.updateMeter(appeal = appeal)
    }

    override fun problem(appeal: AppealProblemOrClaimModel): Flow<Boolean> {
        return appealRepository.problem(appeal = appeal)
    }

    override fun claim(appeal: AppealProblemOrClaimModel): Flow<Boolean> {
        return appealRepository.claim(appeal = appeal)
    }
}