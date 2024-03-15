package com.example.myhome.features.servicenotification.models

import java.util.Date

data class ServiceNotificationListItemModel (
    val id: Int,
    val managementCompanyName: String?,
    val title: String,
    val text: String,
    val type: ServiceNotificationType,
    val createdAt: Date,
    val status: NotificationStatus
)