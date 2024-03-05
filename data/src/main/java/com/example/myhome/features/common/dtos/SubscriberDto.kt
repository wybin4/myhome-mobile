package com.example.myhome.features.common.dtos

import com.example.myhome.models.UserRole

data class SubscriberListRequest (
    val userId: Int,
    val userRole: UserRole = UserRole.Owner
)

data class SubscriberListItemResponse(
    val managementCompanyId: Int,
    val subscriberId: Int,
    val address: String
)