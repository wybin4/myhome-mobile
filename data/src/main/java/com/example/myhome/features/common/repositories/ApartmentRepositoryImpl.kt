package com.example.myhome.features.common.repositories

import com.example.myhome.features.common.mappers.ApartmentRemoteMapper
import com.example.myhome.features.common.models.ApartmentListItemModel
import com.example.myhome.features.common.storages.ApartmentStorage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ApartmentRepositoryImpl(
    private val apartmentStorage: ApartmentStorage,
    private val apartmentRemoteMapper: ApartmentRemoteMapper
): ApartmentRepository {
    override fun listApartment(): Flow<List<ApartmentListItemModel>> = flow {
        val result = apartmentStorage.listApartment()
        emit(apartmentRemoteMapper.mapListToDomain(result))
    }
}
