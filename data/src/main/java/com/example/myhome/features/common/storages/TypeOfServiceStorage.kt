package com.example.myhome.features.common.storages

import com.example.myhome.features.common.CommonApiService
import com.example.myhome.features.common.dtos.TypeOfServiceListItemResponse

class TypeOfServiceStorage(private val commonApiService: CommonApiService) {
    suspend fun listTypeOfService(): List<TypeOfServiceListItemResponse> {
        return commonApiService.listTypeOfService()
    }
}