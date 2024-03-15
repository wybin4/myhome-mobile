package com.example.myhome.features.servicenotification

import com.example.myhome.features.servicenotification.models.NotificationStatus
import com.example.myhome.features.servicenotification.models.ServiceNotificationType
import com.example.myhome.models.DateTimeConverter
import com.example.myhome.models.UserRole
import java.util.Date

data class ServiceNotificationListRequest(
    val userId: Int,
    val userRole: UserRole = UserRole.ManagementCompany
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