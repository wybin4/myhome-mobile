package com.example.myhome.features.common.repositories

import com.example.myhome.features.common.mappers.TypeOfServiceRemoteMapper
import com.example.myhome.features.common.models.TypeOfServiceListItemModel
import com.example.myhome.features.common.storages.TypeOfServiceStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TypeOfServiceRepositoryImpl(
    private val typeOfServiceStorage: TypeOfServiceStorage,
    private val typeOfServiceRemoteMapper: TypeOfServiceRemoteMapper
): TypeOfServiceRepository {
    override fun listTypeOfService(): Flow<List<TypeOfServiceListItemModel>> = flow {
        val result = typeOfServiceStorage.listTypeOfService()
        emit(typeOfServiceRemoteMapper.mapListToDomain(result))
    }

}