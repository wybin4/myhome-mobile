package com.example.myhome.common.repositories

import com.example.myhome.common.mappers.TypeOfServiceRemoteMapper
import com.example.myhome.common.models.TypeOfServiceGetModel
import com.example.myhome.common.storages.TypeOfServiceStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TypeOfServiceRepositoryImpl(
    private val typeOfServiceStorage: TypeOfServiceStorage,
    private val typeOfServiceRemoteMapper: TypeOfServiceRemoteMapper
): TypeOfServiceRepository {
    override fun listTypeOfService(): Flow<List<TypeOfServiceGetModel>> = flow {
        val result = typeOfServiceStorage.listTypeOfService()
        emit(typeOfServiceRemoteMapper.mapListToDomain(result))
    }

}