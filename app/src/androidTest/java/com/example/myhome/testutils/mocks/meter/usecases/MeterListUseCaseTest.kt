package com.example.myhome.testutils.mocks.meter.usecases

import com.example.myhome.meter.models.MeterListItemModel
import com.example.myhome.meter.usecases.MeterListUseCase
import kotlinx.coroutines.flow.Flow

class MeterListUseCaseTest : MeterListUseCase {
    override fun invoke(): Flow<List<MeterListItemModel>> {
        TODO("Not yet implemented")
    }

}
