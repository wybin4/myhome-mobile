package com.example.myhome.meter.usecases

import com.example.myhome.meter.models.MeterAddModel
import com.example.myhome.meter.repositories.MeterRepository
import javax.inject.Inject

class MeterAddUseCase @Inject constructor(
    private val meterRepository: MeterRepository
) {
    suspend operator fun invoke(meter: MeterAddModel) {
        meterRepository.addMeter(meter = meter)
    }

}