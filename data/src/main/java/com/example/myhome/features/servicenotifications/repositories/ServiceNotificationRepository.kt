package com.example.myhome.features.servicenotifications.repositories

import androidx.paging.PagingData
import com.example.myhome.features.event.dto.ServiceNotificationListItemResponse
import kotlinx.coroutines.flow.Flow

interface ServiceNotificationRepository {
    fun listServiceNotification(): Flow<PagingData<ServiceNotificationListItemResponse>>
    suspend fun readNotificationList()
}