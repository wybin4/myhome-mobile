package com.example.myhome.models

import com.example.myhome.features.servicenotification.ServiceNotificationListItemResponse

interface NotificationListener {
    fun onNewNotification(notification: ServiceNotificationListItemResponse)
}