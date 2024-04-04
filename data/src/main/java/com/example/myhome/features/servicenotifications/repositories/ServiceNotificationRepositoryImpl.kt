package com.example.myhome.features.servicenotifications.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myhome.features.event.EventApiService
import com.example.myhome.features.event.dto.ServiceNotificationListItemResponse
import com.example.myhome.features.event.dto.ServiceNotificationReadRequest
import com.example.myhome.features.servicenotifications.pagingsources.ServiceNotificationPagingSourceFactory
import kotlinx.coroutines.flow.Flow

class ServiceNotificationRepositoryImpl(
    private val eventApiService: EventApiService,
    private val serviceNotificationPagingSourceFactory: ServiceNotificationPagingSourceFactory,
    private val pageConfig: PagingConfig
): ServiceNotificationRepository {
    override fun listServiceNotification(): Flow<PagingData<ServiceNotificationListItemResponse>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = { serviceNotificationPagingSourceFactory.create() }
        ).flow
    }

    override suspend fun readNotificationList() {
        val request = ServiceNotificationReadRequest(userId = 1)
        eventApiService.readServiceNotificationList(request)
    }

    override fun addServiceNotification(): Flow<PagingData<ServiceNotificationListItemResponse>> {
        serviceNotificationPagingSourceFactory.create().invalidate()
        return Pager(
            config = pageConfig,
            pagingSourceFactory = { serviceNotificationPagingSourceFactory.create() }
        ).flow
    }
}