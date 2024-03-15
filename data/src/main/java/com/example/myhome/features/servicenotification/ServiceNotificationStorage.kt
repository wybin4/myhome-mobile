package com.example.myhome.features.servicenotification

import com.example.myhome.features.SocketService
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class ServiceNotificationStorage(private val socketService: SocketService) {
    suspend fun listNotification(): List<ServiceNotificationListItemResponse> =
        suspendCancellableCoroutine { continuation ->
            socketService.connect()
            socketService.setNotificationsListener { notifications ->
                continuation.resume(notifications)
            }
        }
}