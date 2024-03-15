package com.example.myhome.testutils.mocks.common.repositories

import com.example.myhome.features.common.dtos.TypeOfServiceListItemResponse
import com.example.myhome.features.common.repositories.TypeOfServiceRepository
import com.example.myhome.testutils.CommonDomainTestListProvider.getTypeOfServiceList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TypeOfServiceRepositoryTest : TypeOfServiceRepository {
    override fun listTypeOfService(): Flow<List<TypeOfServiceListItemResponse>> {
        return flowOf(getTypeOfServiceList())
    }
}
