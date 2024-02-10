package com.example.myhome.common.storages

import com.example.myhome.common.dtos.TypeOfServiceGetDto
import com.example.myhome.common.services.TypeOfServiceApiService

class TypeOfServiceRemoteStorage(private val typeOfServiceApiService: TypeOfServiceApiService): TypeOfServiceStorage {
    override suspend fun listTypeOfService(): List<TypeOfServiceGetDto> {
        return typeOfServiceApiService.listTypeOfService()
    }
}