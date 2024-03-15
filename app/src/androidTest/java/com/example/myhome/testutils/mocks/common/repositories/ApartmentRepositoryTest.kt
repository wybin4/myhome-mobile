package com.example.myhome.testutils.mocks.common.repositories

import com.example.myhome.features.common.dtos.ApartmentListItemResponse
import com.example.myhome.features.common.repositories.ApartmentRepository
import com.example.myhome.testutils.CommonDomainTestListProvider.getApartmentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ApartmentRepositoryTest : ApartmentRepository {
    override fun listApartment(): Flow<List<ApartmentListItemResponse>> {
        return flowOf(getApartmentList())
    }
}
