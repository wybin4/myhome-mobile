package com.example.myhome.common.repositories

import com.example.myhome.common.models.ApartmentGetModel
import kotlinx.coroutines.flow.Flow

interface ApartmentRepository {
    fun listApartment(): Flow<List<ApartmentGetModel>>
}