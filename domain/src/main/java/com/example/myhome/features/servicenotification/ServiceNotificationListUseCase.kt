package com.example.myhome.features.servicenotification

import com.example.myhome.features.servicenotification.models.ServiceNotificationListItemModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ServiceNotificationListUseCase @Inject constructor(
    private val notificationRepository: ServiceNotificationRepository
) {
    operator fun invoke(): Flow<List<ServiceNotificationListItemModel>> {
        return notificationRepository.listNotification()
    }
}
