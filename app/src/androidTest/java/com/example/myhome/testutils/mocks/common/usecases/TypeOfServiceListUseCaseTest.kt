package com.example.myhome.testutils.mocks.common.usecases

import com.example.myhome.common.models.TypeOfServiceGetModel
import com.example.myhome.common.usecases.TypeOfServiceListUseCase
import com.example.myhome.testutils.providers.CommonDomainTestListProvider.getTypeOfServiceList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TypeOfServiceListUseCaseTest : TypeOfServiceListUseCase {
    override operator fun invoke(): Flow<List<TypeOfServiceGetModel>> {
        return flowOf(getTypeOfServiceList())
    }
}
