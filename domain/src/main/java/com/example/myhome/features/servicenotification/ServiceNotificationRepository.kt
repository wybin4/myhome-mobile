package com.example.myhome.features.servicenotification

import com.example.myhome.features.servicenotification.models.ServiceNotificationListItemModel
import kotlinx.coroutines.flow.Flow

interface ServiceNotificationRepository {
    fun listNotification(): Flow<List<ServiceNotificationListItemModel>>
}