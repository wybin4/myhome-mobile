package com.example.myhome.features.common.repositories

import com.example.myhome.features.common.dtos.ApartmentListItemResponse
import kotlinx.coroutines.flow.Flow

interface ApartmentRepository {
    fun listApartment(): Flow<List<ApartmentListItemResponse>>
}