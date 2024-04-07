package com.example.myhome.features.common

import com.example.myhome.features.common.dtos.ApartmentListRequest
import com.example.myhome.features.common.dtos.ApartmentListResponse
import com.example.myhome.features.common.dtos.SubscriberListResponse
import com.example.myhome.features.common.dtos.TypeOfServiceListResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface CommonApiService {
    @POST("apartment/get-apartments-by-user")
    suspend fun listApartment(
        @Body request: ApartmentListRequest
    ): ApartmentListResponse

    @POST("user/get-users-by-another-role")
    suspend fun listSubscriber(): SubscriberListResponse

    @POST("common/get-all-types-of-service")
    suspend fun listTypeOfService(): TypeOfServiceListResponse

}