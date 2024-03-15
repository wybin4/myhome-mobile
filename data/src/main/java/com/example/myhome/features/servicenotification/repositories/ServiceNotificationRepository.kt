package com.example.myhome.features.servicenotification.repositories

import com.example.myhome.features.servicenotification.ServiceNotificationListItemResponse
import kotlinx.coroutines.flow.Flow

interface ServiceNotificationRepository {
    fun listNotification(): Flow<List<ServiceNotificationListItemResponse>>
}