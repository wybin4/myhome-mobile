package com.example.myhome.presentation.features.servicenotification

import com.example.myhome.features.servicenotification.models.ServiceNotificationListItemModel
import com.example.myhome.features.servicenotification.models.ServiceNotificationType
import com.example.myhome.presentation.features.servicenotification.models.ServiceNotificationUiModel
import com.example.myhome.presentation.features.servicenotification.models.ServiceNotificationUiType
import javax.inject.Inject

class ServiceNotificationUiConverter @Inject constructor() {
    fun notificationListToUi(notifications: List<ServiceNotificationListItemModel>): List<ServiceNotificationUiModel> {
        return notifications.map {
            ServiceNotificationUiModel(
                id = it.id,
                managementCompanyName = it.managementCompanyName,
                title = it.title,
                text = it.text,
                status = it.status,
                type = getType(it.type),
                createdAt = it.createdAt
            )
        }.sortedByDescending { it.createdAt }
    }

    private fun getType(type: ServiceNotificationType): ServiceNotificationUiType {
        return when(type) {
            ServiceNotificationType.Appeal -> ServiceNotificationUiType.System
            ServiceNotificationType.HouseNotification -> ServiceNotificationUiType.User
            ServiceNotificationType.Meter -> ServiceNotificationUiType.System
            else -> ServiceNotificationUiType.None
        }
    }

}