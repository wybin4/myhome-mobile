package com.example.myhome.common.dtos

data class ApartmentListDto(
    val isAllInfo: Boolean = true
)

data class ApartmentGetDto(
    val id: Int,
    val houseId: Int,
    val apartmentNumber: Int,
    val totalArea: Double,
    val livingArea: Double,
    val numberOfRegistered: Int,
    val address: String,
    val subscriberId: Int,
)