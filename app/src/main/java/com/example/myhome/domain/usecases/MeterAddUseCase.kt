package com.example.myhome.domain.usecases

import com.example.myhome.domain.models.MeterAddModel
import com.example.myhome.domain.repositories.MeterRepository
import javax.inject.Inject

class MeterAddUseCase @Inject constructor(
    private val meterRepository: MeterRepository
) {
    suspend operator fun invoke(meter: MeterAddModel) {
        meterRepository.addMeter(meter = meter)
    }

}