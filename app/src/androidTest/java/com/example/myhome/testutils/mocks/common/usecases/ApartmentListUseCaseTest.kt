package com.example.myhome.testutils.mocks.common.usecases

import com.example.myhome.features.common.models.ApartmentListItemModel
import com.example.myhome.features.common.usecases.ApartmentListUseCase
import com.example.myhome.testutils.providers.CommonDomainTestListProvider.getApartmentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ApartmentListUseCaseTest : ApartmentListUseCase {
    override operator fun invoke(): Flow<List<ApartmentListItemModel>> {
        return flowOf(getApartmentList())
    }
}
