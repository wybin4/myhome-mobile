package com.example.myhome.features.common.storages

import com.example.myhome.features.common.dtos.ApartmentListItemResponse
import com.example.myhome.features.common.dtos.ApartmentListRequest
import com.example.myhome.features.common.CommonApiService

class ApartmentStorage(private val commonApiService: CommonApiService) {
    suspend fun listApartment(): List<ApartmentListItemResponse> {
        val request = ApartmentListRequest()
        return commonApiService.listApartment(request)
    }
}