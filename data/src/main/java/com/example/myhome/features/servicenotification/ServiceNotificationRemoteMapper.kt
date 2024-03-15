package com.example.myhome.features.servicenotification

import com.example.myhome.features.servicenotification.models.ServiceNotificationListItemModel

class ServiceNotificationRemoteMapper {
    fun mapListToDomain(notifications: List<ServiceNotificationListItemResponse>): List<ServiceNotificationListItemModel> {
        return notifications.map {
            ServiceNotificationListItemModel(
                 id = it.id,
                 managementCompanyName = it.name,
                 title = it.title,
                 text = it.text,
                 type = it.type,
                 createdAt = it.formatCreatedAt(),
                 status = it.status
            )
        }
    }
}
