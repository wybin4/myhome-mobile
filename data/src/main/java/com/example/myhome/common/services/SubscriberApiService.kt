package com.example.myhome.common.services

import com.example.myhome.common.dtos.SubscriberGetDto
import com.example.myhome.common.dtos.SubscriberListDto
import retrofit2.http.Body
import retrofit2.http.POST

interface SubscriberApiService {
    @POST("user/get-users-by-another-role")
    suspend fun listSubscriber(
        @Body request: SubscriberListDto
    ): List<SubscriberGetDto>
}