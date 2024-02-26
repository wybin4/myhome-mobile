package com.example.myhome.testutils.mocks.common.usecases

import com.example.myhome.common.models.ApartmentGetModel
import com.example.myhome.common.usecases.ApartmentListUseCase
import com.example.myhome.testutils.providers.CommonDomainTestListProvider.getApartmentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ApartmentListUseCaseTest : ApartmentListUseCase {
    override operator fun invoke(): Flow<List<ApartmentGetModel>> {
        return flowOf(getApartmentList())
    }
}
