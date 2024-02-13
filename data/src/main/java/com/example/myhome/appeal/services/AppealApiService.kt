package com.example.myhome.appeal.services

import com.example.myhome.appeal.dtos.AppealAddDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AppealApiService {
    @POST("appeal/add-appeal")
    suspend fun addAppeal(
        @Body request: AppealAddDto
    )
}