package com.example.myhome.features.event.dto

import com.example.myhome.features.servicenotifications.models.NotificationStatus
import com.example.myhome.features.servicenotifications.models.ServiceNotificationType
import com.example.myhome.models.DateTimeConverter
import com.example.myhome.models.MetaRequest
import com.example.myhome.models.UserRole
import java.util.Date

data class ServiceNotificationListRequest(
    val meta: MetaRequest
)

data class ServiceNotificationListResponse(
    val notifications: List<ServiceNotificationListItemResponse>
)

data class ServiceNotificationListItemResponse(
    val id: Int,
    val userId: Int,
    val userRole: UserRole,
    val name: String?,
    val title: String,
    val description: String?,
    val text: String,
    val type: ServiceNotificationType,
    val createdAt: String,
    val readAt: String?,
    val status: NotificationStatus
): DateTimeConverter {
    fun formatCreatedAt(): Date {
        return parseDateTime(createdAt)
    }
}