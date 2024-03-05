package com.example.myhome.features.event

import com.example.myhome.features.event.dto.EventListResponse
import com.example.myhome.features.event.dto.EventListRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface EventApiService {
    @POST("event/get-events")
    suspend fun listEvent(
        @Body request: EventListRequest
    ): EventListResponse
}