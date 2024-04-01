package com.example.myhome.features.common.dtos

import com.example.myhome.models.UserRole

data class ApartmentListRequest(
    val userId: Int,
    val userRole: UserRole = UserRole.Owner,
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