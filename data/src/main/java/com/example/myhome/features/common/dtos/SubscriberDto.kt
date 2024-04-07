package com.example.myhome.features.common.dtos

data class SubscriberListResponse(
    val users: List<SubscriberListItemResponse>
)

data class SubscriberListItemResponse(
    val user: ManagementCompanyListItemResponse,
    val subscribers: List<ManagementCompanyWithSubscriberListItemResponse>
)