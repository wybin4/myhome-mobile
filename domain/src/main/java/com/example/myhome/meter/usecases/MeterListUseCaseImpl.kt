package com.example.myhome.meter.usecases

import com.example.myhome.meter.models.MeterListItemModel
import com.example.myhome.meter.repositories.MeterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MeterListUseCaseImpl @Inject constructor(
    private val meterRepository: MeterRepository
): MeterListUseCase {
    override operator fun invoke(): Flow<List<MeterListItemModel>> {
        return meterRepository.listMeter()
    }
}
