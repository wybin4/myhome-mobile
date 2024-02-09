package com.example.myhome.domain.meter.usecases

import com.example.myhome.domain.meter.models.MeterGetModel
import com.example.myhome.domain.meter.repositories.MeterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MeterListUseCase @Inject constructor(
    private val meterRepository: MeterRepository
) {
    operator fun invoke(): Flow<List<MeterGetModel>> {
        return meterRepository.listMeter()
    }
}
