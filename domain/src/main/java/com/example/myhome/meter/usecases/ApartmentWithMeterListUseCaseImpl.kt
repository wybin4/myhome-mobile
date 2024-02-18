package com.example.myhome.meter.usecases

import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.meter.repositories.MeterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApartmentWithMeterListUseCaseImpl @Inject constructor(
    private val meterRepository: MeterRepository
): ApartmentWithMeterListUseCase {
    override operator fun invoke(): Flow<List<ApartmentWithMeterGetModel>> {
        return meterRepository.listApartmentWithMeter()
    }
}
