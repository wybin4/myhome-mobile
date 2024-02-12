package com.example.myhome.common.dtos

import com.example.myhome.base.models.UserRole

data class SubscriberListDto (
    val userId: Int,
    val userRole: UserRole = UserRole.Owner
)

data class SubscriberGetDto(
    val managementCompanyId: Int,
    val subscriberId: Int,
    val address: String
)