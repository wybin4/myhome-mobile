package com.example.myhome.common.storages

import com.example.myhome.common.dtos.TypeOfServiceGetDto

interface TypeOfServiceStorage {
    suspend fun listTypeOfService(): List<TypeOfServiceGetDto>
}