package com.example.myhome.testutils.mocks.meter.usecases

import com.example.myhome.features.meter.models.ApartmentWithMeterListItemModel
import com.example.myhome.features.meter.usecases.ApartmentWithMeterListUseCase
import com.example.myhome.presentation.testutils.providers.MeterUITestListProvider.getApartmentWithMeterList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ApartmentWithMeterListUseCaseTest : ApartmentWithMeterListUseCase {
    override operator fun invoke(): Flow<List<ApartmentWithMeterListItemModel>> {
        return flowOf(getApartmentWithMeterList())
    }
}
