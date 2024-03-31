package com.example.myhome.features.servicenotifications.pagingsources

import androidx.paging.PagingState
import com.example.myhome.features.CustomPagingSource
import com.example.myhome.features.event.EventApiService
import com.example.myhome.features.event.dto.ServiceNotificationListItemResponse
import com.example.myhome.features.event.dto.ServiceNotificationListRequest
import com.example.myhome.models.MetaRequest



class ServiceNotificationPagingSource(
    private val eventApiService: EventApiService
) : CustomPagingSource<ServiceNotificationListItemResponse>() {
    companion object {
        const val NOTIFICATION_PAGE_SIZE = 6
    }

    override suspend fun invoke(page: Int): List<ServiceNotificationListItemResponse> {
        return eventApiService.listServiceNotification(
            ServiceNotificationListRequest(
                userId = 1,
                meta = MetaRequest(page, NOTIFICATION_PAGE_SIZE)
            )
        )
    }

    override fun getRefreshKey(state: PagingState<Int, ServiceNotificationListItemResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}