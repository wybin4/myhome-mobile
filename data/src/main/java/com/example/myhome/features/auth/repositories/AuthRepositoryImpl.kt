package com.example.myhome.features.auth.repositories

import com.example.myhome.features.auth.JwtTokenStore
import com.example.myhome.features.auth.dtos.LoginRequest
import com.example.myhome.features.auth.services.AuthApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val jwtTokenStore: JwtTokenStore
): AuthRepository {
    override fun login(request: LoginRequest): Flow<Boolean> = flow {
        val response = authApiService.login(request)
        jwtTokenStore.saveAccessJwt(response.token)
        jwtTokenStore.saveRefreshJwt(response.refreshToken)
        emit(true)
    }

    override suspend fun isLoginNeed(): Boolean {
        val accessJwt = jwtTokenStore.getAccessJwt()
        val refreshJwt = jwtTokenStore.getRefreshJwt()
        return accessJwt.isNullOrEmpty() || refreshJwt.isNullOrEmpty()
    }

    override suspend fun logout() {
        jwtTokenStore.clearAllTokens()
    }

}
