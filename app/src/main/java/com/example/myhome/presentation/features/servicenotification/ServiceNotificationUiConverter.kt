package com.example.myhome.presentation.features.servicenotification

import com.example.myhome.features.servicenotification.ServiceNotificationListItemResponse
import com.example.myhome.presentation.features.servicenotification.models.ServiceNotificationUiModel
import com.example.myhome.presentation.features.servicenotification.models.ServiceNotificationUiType
import javax.inject.Inject

class ServiceNotificationUiConverter @Inject constructor() {
    fun notificationListToUi(notifications: List<ServiceNotificationListItemResponse>): List<ServiceNotificationUiModel> {
        return notifications.map {
            ServiceNotificationUiModel(
                id = it.id,
                managementCompanyName = it.name,
                title = it.title,
                text = it.text,
                status = it.status,
                type = ServiceNotificationUiType.fromServiceNotificationType(it.type),
                createdAt = it.formatCreatedAt()
            )
        }.sortedByDescending { it.createdAt }
    }

}