package com.example.myhome.features.common.repositories

import com.example.myhome.features.common.dtos.TypeOfServiceListItemResponse
import kotlinx.coroutines.flow.Flow

interface TypeOfServiceRepository {
    fun listTypeOfService(): Flow<List<TypeOfServiceListItemResponse>>
}