package com.example.myhome.common.dtos

data class ApartmentListDto(
    val isAllInfo: Boolean = true
)

data class ApartmentGetDto(
    val id: Int,
    val houseId: Int,
    val apartmentNumber: Int,
    val totalArea: Float,
    val livingArea: Float,
    val numberOfRegistered: Int,
    val address: String,
    val subscriberId: Int,
)