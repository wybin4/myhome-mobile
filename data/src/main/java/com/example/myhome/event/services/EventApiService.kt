package com.example.myhome.event.services

import com.example.myhome.event.dto.EventGetDto
import com.example.myhome.event.dto.EventListDto
import retrofit2.http.Body
import retrofit2.http.POST

interface EventApiService {
    @POST("event/get-events")
    suspend fun listEvents(
        @Body request: EventListDto
    ): EventGetDto
}