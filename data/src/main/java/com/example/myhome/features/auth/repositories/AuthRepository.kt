package com.example.myhome.features.auth.repositories

import com.example.myhome.features.auth.dtos.LoginRequest
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun login(request: LoginRequest): Flow<Boolean>
    suspend fun isLoginNeed(): Boolean
    suspend fun logout()
}