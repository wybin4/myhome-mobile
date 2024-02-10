package com.example.myhome.common.repositories

import com.example.myhome.common.models.TypeOfServiceGetModel
import kotlinx.coroutines.flow.Flow

interface TypeOfServiceRepository {
    fun listTypeOfService(): Flow<List<TypeOfServiceGetModel>>
}