package com.example.myhome.testutils.mocks.common.repositories

import com.example.myhome.features.common.models.ApartmentListItemModel
import com.example.myhome.features.common.repositories.ApartmentRepository
import com.example.myhome.testutils.providers.CommonDomainTestListProvider.getApartmentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ApartmentRepositoryTest : ApartmentRepository {
    override fun listApartment(): Flow<List<ApartmentListItemModel>> {
        return flowOf(getApartmentList())
    }
}
