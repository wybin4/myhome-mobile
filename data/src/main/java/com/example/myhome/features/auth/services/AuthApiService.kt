package com.example.myhome.features.auth.services

import com.example.myhome.features.auth.dtos.LoginRequest
import com.example.myhome.features.auth.dtos.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {
    @POST("auth/login")
    suspend fun login(
        @Body body: LoginRequest
    ): LoginResponse
}