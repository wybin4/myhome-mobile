package com.example.myhome.features.common.dtos

import com.example.myhome.models.UserRole

data class SubscriberListRequest (
    val userId: Int,
    val userRole: UserRole = UserRole.Owner
)

data class SubscriberListResponse(
    val users: List<SubscriberListItemResponse>
)

data class SubscriberListItemResponse(
    val user: ManagementCompanyListItemResponse,
    val subscribers: List<ManagementCompanyWithSubscriberListItemResponse>
)