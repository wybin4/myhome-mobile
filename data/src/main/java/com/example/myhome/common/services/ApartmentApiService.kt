package com.example.myhome.common.services

import com.example.myhome.common.dtos.ApartmentGetDto
import com.example.myhome.common.dtos.ApartmentListDto
import retrofit2.http.Body
import retrofit2.http.POST

interface ApartmentApiService {
    @POST("apartment/get-apartments-by-user")
    suspend fun listApartment(
        @Body request: ApartmentListDto
    ): List<ApartmentGetDto>
}