package com.example.myhome.appeal.usecases

import com.example.myhome.appeal.models.AppealAddMeterModel
import com.example.myhome.appeal.models.AppealUpdateMeterModel
import com.example.myhome.appeal.repositories.AppealRepository
import javax.inject.Inject

class AppealAddUseCaseImpl @Inject constructor(
    private val appealRepository: AppealRepository
) : AppealAddUseCase {
    override suspend fun addMeter(appeal: AppealAddMeterModel) {
        appealRepository.addMeter(appeal = appeal)
    }

    override suspend fun updateMeter(appeal: AppealUpdateMeterModel) {
        appealRepository.updateMeter(appeal = appeal)
    }
}