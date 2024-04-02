package com.example.myhome.features.appeal

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap

@JvmSuppressWildcards
interface AppealApiService {
    @Multipart
    @POST("appeal/add-appeal")
    suspend fun addAppealWithFile(
        @Part file: MultipartBody.Part,
        @PartMap data: Map<String, RequestBody>,
    ): AppealListItemResponse
    
    @Multipart
    @POST("appeal/add-appeal")
    suspend fun addAppeal(
        @PartMap data: Map<String, RequestBody>,
    ): AppealListItemResponse
}