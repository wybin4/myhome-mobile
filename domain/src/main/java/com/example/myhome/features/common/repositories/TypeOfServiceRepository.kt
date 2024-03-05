package com.example.myhome.features.common.repositories

import com.example.myhome.features.common.models.TypeOfServiceListItemModel
import kotlinx.coroutines.flow.Flow

interface TypeOfServiceRepository {
    fun listTypeOfService(): Flow<List<TypeOfServiceListItemModel>>
}