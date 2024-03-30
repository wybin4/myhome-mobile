package com.example.myhome.presentation.features.servicenotification

import com.example.myhome.features.event.dto.ServiceNotificationListItemResponse
import com.example.myhome.presentation.features.servicenotification.models.ServiceNotificationUiModel
import com.example.myhome.presentation.features.servicenotification.models.ServiceNotificationUiType
import javax.inject.Inject

class ServiceNotificationUiConverter @Inject constructor() {
    fun notificationToUi(
        notification: ServiceNotificationListItemResponse
    ): ServiceNotificationUiModel {
        return notification.run {
            ServiceNotificationUiModel(
                id = id,
                managementCompanyName = name,
                title = title,
                text = text,
                status = status,
                type = ServiceNotificationUiType.fromServiceNotificationType(type),
                createdAt = formatCreatedAt()
            )
        }
    }

}