package com.example.myhome.features.servicenotification

import com.example.myhome.features.servicenotification.models.ServiceNotificationListItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ServiceNotificationRepositoryImpl(
    private val serviceNotificationStorage: ServiceNotificationStorage,
    private val notificationRemoteMapper: ServiceNotificationRemoteMapper
): ServiceNotificationRepository {
    override fun listNotification(): Flow<List<ServiceNotificationListItemModel>> = flow {
        val notifications = serviceNotificationStorage.listNotification()
        emit(notificationRemoteMapper.mapListToDomain(notifications))
    }

}
