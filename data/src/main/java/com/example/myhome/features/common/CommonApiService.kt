package com.example.myhome.features.common

import com.example.myhome.features.common.dtos.ApartmentListItemResponse
import com.example.myhome.features.common.dtos.ApartmentListRequest
import com.example.myhome.features.common.dtos.SubscriberListRequest
import com.example.myhome.features.common.dtos.SubscriberListItemResponse
import com.example.myhome.features.common.dtos.TypeOfServiceListItemResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface CommonApiService {
    @POST("apartment/get-apartments-by-user")
    suspend fun listApartment(
        @Body request: ApartmentListRequest
    ): List<ApartmentListItemResponse>

    @POST("user/get-users-by-another-role")
    suspend fun listSubscriber(
        @Body request: SubscriberListRequest
    ): List<SubscriberListItemResponse>

    @POST("common/get-all-types-of-service")
    suspend fun listTypeOfService(): List<TypeOfServiceListItemResponse>

}