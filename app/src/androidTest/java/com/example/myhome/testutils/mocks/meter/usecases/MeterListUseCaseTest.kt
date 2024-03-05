package com.example.myhome.testutils.mocks.meter.usecases

import com.example.myhome.features.meter.models.MeterListItemExtendedModel
import com.example.myhome.features.meter.usecases.MeterListUseCase
import kotlinx.coroutines.flow.Flow

class MeterListUseCaseTest : MeterListUseCase {
    override fun invoke(): Flow<List<MeterListItemExtendedModel>> {
        TODO("Not yet implemented")
    }

}
