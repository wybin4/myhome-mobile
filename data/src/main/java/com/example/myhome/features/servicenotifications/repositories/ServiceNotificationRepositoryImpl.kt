package com.example.myhome.features.servicenotifications.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myhome.features.event.EventApiService
import com.example.myhome.features.event.dto.ServiceNotificationListItemResponse
import com.example.myhome.features.servicenotifications.ServiceNotificationPagingSource
import kotlinx.coroutines.flow.Flow

class ServiceNotificationRepositoryImpl(
    private val eventApiService: EventApiService,
    private val serviceNotificationPagingSource: ServiceNotificationPagingSource,
    private val pageConfig: PagingConfig
): ServiceNotificationRepository {
    override fun listServiceNotification(): Flow<PagingData<ServiceNotificationListItemResponse>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = { serviceNotificationPagingSource }
        ).flow
    }

    override suspend fun readNotificationList() {
        eventApiService.readServiceNotificationList()
    }
}