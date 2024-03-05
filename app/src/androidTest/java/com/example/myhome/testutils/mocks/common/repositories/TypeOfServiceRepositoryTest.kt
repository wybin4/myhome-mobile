package com.example.myhome.testutils.mocks.common.repositories

import com.example.myhome.features.common.models.TypeOfServiceListItemModel
import com.example.myhome.features.common.repositories.TypeOfServiceRepository
import com.example.myhome.testutils.providers.CommonDomainTestListProvider.getTypeOfServiceList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TypeOfServiceRepositoryTest : TypeOfServiceRepository {
    override fun listTypeOfService(): Flow<List<TypeOfServiceListItemModel>> {
        return flowOf(getTypeOfServiceList())
    }
}
