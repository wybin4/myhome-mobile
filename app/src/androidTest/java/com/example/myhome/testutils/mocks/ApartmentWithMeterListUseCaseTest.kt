package com.example.myhome.testutils.mocks

import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.meter.usecases.ApartmentWithMeterListUseCase
import com.example.myhome.testutils.providers.MeterUITestListProvider.getApartmentWithMeterList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ApartmentWithMeterListUseCaseTest : ApartmentWithMeterListUseCase {
    override operator fun invoke(): Flow<List<ApartmentWithMeterGetModel>> {
        return flowOf(getApartmentWithMeterList())
    }
}
