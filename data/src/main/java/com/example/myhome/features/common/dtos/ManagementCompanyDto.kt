package com.example.myhome.features.common.dtos

data class ManagementCompanyWithSubscriberListItemResponse(
    val id: Int,
    val address: String
)

data class ManagementCompanyListItemResponse(
    val id: Int,
    val name: String,
    val email: String,
    val checkingAccount: String
)