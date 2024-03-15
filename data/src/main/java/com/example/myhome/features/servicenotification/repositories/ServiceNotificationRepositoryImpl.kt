package com.example.myhome.features.servicenotification.repositories

import com.example.myhome.features.servicenotification.ServiceNotificationListItemResponse
import com.example.myhome.features.servicenotification.ServiceNotificationStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ServiceNotificationRepositoryImpl(
    private val serviceNotificationStorage: ServiceNotificationStorage
): ServiceNotificationRepository {
    override fun listNotification(): Flow<List<ServiceNotificationListItemResponse>> = flow {
        emit(serviceNotificationStorage.listNotification())
    }

}
