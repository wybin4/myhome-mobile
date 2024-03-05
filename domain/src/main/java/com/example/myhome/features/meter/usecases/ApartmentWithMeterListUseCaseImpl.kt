package com.example.myhome.features.meter.usecases

import com.example.myhome.features.meter.models.ApartmentWithMeterListItemModel
import com.example.myhome.features.meter.repositories.MeterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApartmentWithMeterListUseCaseImpl @Inject constructor(
    private val meterRepository: MeterRepository
): ApartmentWithMeterListUseCase {
    override operator fun invoke(): Flow<List<ApartmentWithMeterListItemModel>> {
        return meterRepository.listApartmentWithMeter()
    }
}
