package com.example.myhome.testutils.mocks.common.repositories

import com.example.myhome.common.models.ApartmentGetModel
import com.example.myhome.common.repositories.ApartmentRepository
import com.example.myhome.testutils.providers.CommonDomainTestListProvider.getApartmentList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ApartmentRepositoryTest : ApartmentRepository {
    override fun listApartment(): Flow<List<ApartmentGetModel>> {
        return flowOf(getApartmentList())
    }
}
