package com.example.myhome.features.common.repositories

import com.example.myhome.features.common.CommonApiService
import com.example.myhome.features.common.dtos.TypeOfServiceListItemResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TypeOfServiceRepositoryImpl(
    private val commonApiService: CommonApiService
): TypeOfServiceRepository {
    override fun listTypeOfService(): Flow<List<TypeOfServiceListItemResponse>> = flow {
        val response = commonApiService.listTypeOfService()
        emit(response.typesOfService)
    }

}