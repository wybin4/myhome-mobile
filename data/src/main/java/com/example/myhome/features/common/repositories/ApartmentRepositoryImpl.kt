package com.example.myhome.features.common.repositories

import com.example.myhome.features.common.CommonApiService
import com.example.myhome.features.common.dtos.ApartmentListItemResponse
import com.example.myhome.features.common.dtos.ApartmentListRequest
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApartmentRepositoryImpl(
    private val commonApiService: CommonApiService
): ApartmentRepository {
    override fun listApartment(): Flow<List<ApartmentListItemResponse>> = flow {
        val response = commonApiService.listApartment(ApartmentListRequest())
        emit(response.apartments)
    }
}
