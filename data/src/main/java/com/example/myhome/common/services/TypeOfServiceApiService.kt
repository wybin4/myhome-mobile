package com.example.myhome.common.services

import com.example.myhome.common.dtos.TypeOfServiceGetDto
import retrofit2.http.POST

interface TypeOfServiceApiService {
    @POST("common/get-all-types-of-service")
    suspend fun listTypeOfService(): List<TypeOfServiceGetDto>
}