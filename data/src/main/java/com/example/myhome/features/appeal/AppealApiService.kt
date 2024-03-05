package com.example.myhome.features.appeal

import retrofit2.http.Body
import retrofit2.http.POST

interface AppealApiService {
    @POST("appeal/add-appeal")
    suspend fun addAppeal(
        @Body request: AppealAddRequest
    ): AppealAddResponse
}