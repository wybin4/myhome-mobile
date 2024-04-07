package com.example.myhome.features.common.dtos

data class ApartmentListRequest(
    val isAllInfo: Boolean = true
)

data class ApartmentListResponse(
    val apartments: List<ApartmentListItemResponse>
)

data class ApartmentListItemResponse(
    val id: Int,
    val houseId: Int,
    val apartmentNumber: Int,
    val totalArea: Double,
    val livingArea: Double,
    val numberOfRegistered: Int,
    val address: String,
    val subscriberId: Int,
)