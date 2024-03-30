package com.example.myhome.features.event

import com.example.myhome.features.event.dto.EventListRequest
import com.example.myhome.features.event.dto.EventListResponse
import com.example.myhome.features.event.dto.ServiceNotificationListItemResponse
import com.example.myhome.features.event.dto.ServiceNotificationListRequest
import com.example.myhome.features.event.dto.VoteListItemResponse
import com.example.myhome.features.event.dto.VotingUpdateRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface EventApiService {
    @POST("event/get-events")
    suspend fun listEvent(
        @Body request: EventListRequest
    ): EventListResponse

    @POST("event/get-service-notifications")
    suspend fun listServiceNotification(
        @Body request: ServiceNotificationListRequest
    ): List<ServiceNotificationListItemResponse>

    @POST("event/read-service-notifications")
    suspend fun readServiceNotificationList()

    @POST("event/update-voting")
    suspend fun updateVoting(
        @Body request: VotingUpdateRequest
    ): VoteListItemResponse

}