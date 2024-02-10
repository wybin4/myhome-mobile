package com.example.myhome.common.storages

import com.example.myhome.common.dtos.ApartmentGetDto
import com.example.myhome.common.dtos.ApartmentListDto
import com.example.myhome.common.services.ApartmentApiService

class ApartmentRemoteStorage(private val apartmentApiService: ApartmentApiService): ApartmentStorage {
    override suspend fun listApartment(): List<ApartmentGetDto> {
        val request = ApartmentListDto()
        return apartmentApiService.listApartment(request)
    }
}