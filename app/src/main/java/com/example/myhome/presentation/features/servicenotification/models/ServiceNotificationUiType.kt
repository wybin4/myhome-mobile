package com.example.myhome.presentation.features.servicenotification.models

import com.example.myhome.features.servicenotification.models.ServiceNotificationType

enum class ServiceNotificationUiType {
    System,
    User,
    None;

    companion object {
        fun fromServiceNotificationType(type: ServiceNotificationType): ServiceNotificationUiType {
            return when (type) {
                ServiceNotificationType.Appeal -> System
                ServiceNotificationType.HouseNotification -> User
                ServiceNotificationType.Meter -> System
                else -> None
            }
        }
    }
}
