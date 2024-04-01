package com.example.myhome.features.common.dtos

data class TypeOfServiceListResponse(
    val typesOfService: List<TypeOfServiceListItemResponse>
)

data class TypeOfServiceListItemResponse(
    val id: Int,
    val name: String,
    val unitId: Int,
    val engName: String
)