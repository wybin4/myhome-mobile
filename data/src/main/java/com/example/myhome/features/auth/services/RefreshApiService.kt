package com.example.myhome.features.auth.services

import com.example.myhome.features.auth.dtos.RefreshResponse
import retrofit2.Response
import retrofit2.http.POST

interface RefreshApiService {
    @POST("auth/refresh")
    suspend fun refreshToken(): Response<RefreshResponse>
}