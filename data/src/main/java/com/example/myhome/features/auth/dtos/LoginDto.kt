package com.example.myhome.features.auth.dtos

import com.example.myhome.models.UserRole

data class LoginRequest (
    val email: String,
    val password: String,
    val userRole: UserRole = UserRole.Owner
)

data class LoginResponse (
    val token: String,
    val refreshToken: String
)