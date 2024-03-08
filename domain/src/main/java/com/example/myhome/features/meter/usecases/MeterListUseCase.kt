package com.example.myhome.features.meter.usecases

import com.example.myhome.features.meter.models.MeterListItemExtendedModel
import com.example.myhome.features.meter.repositories.MeterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MeterListUseCase @Inject constructor(
    private val meterRepository: MeterRepository
) {
    operator fun invoke(): Flow<List<MeterListItemExtendedModel>> {
        return meterRepository.listMeter()
    }
}
