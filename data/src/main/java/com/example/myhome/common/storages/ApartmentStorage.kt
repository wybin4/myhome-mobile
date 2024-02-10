package com.example.myhome.common.storages

import com.example.myhome.common.dtos.ApartmentGetDto

interface ApartmentStorage {
    suspend fun listApartment(): List<ApartmentGetDto>
}