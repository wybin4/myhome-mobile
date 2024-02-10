package com.example.myhome.common.repositories

import com.example.myhome.common.mappers.ApartmentRemoteMapper
import com.example.myhome.common.models.ApartmentGetModel
import com.example.myhome.common.storages.ApartmentStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApartmentRepositoryImpl(
    private val apartmentStorage: ApartmentStorage,
    private val apartmentRemoteMapper: ApartmentRemoteMapper
): ApartmentRepository {
    override fun listApartment(): Flow<List<ApartmentGetModel>> = flow {
        val result = apartmentStorage.listApartment()
        emit(apartmentRemoteMapper.mapListToDomain(result))
    }
}
