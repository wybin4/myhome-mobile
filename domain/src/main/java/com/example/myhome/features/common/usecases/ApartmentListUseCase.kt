package com.example.myhome.features.common.usecases

import com.example.myhome.features.common.models.ApartmentListItemModel
import com.example.myhome.features.common.repositories.ApartmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApartmentListUseCase @Inject constructor(
    private val apartmentRepository: ApartmentRepository
) {
    operator fun invoke(): Flow<List<ApartmentListItemModel>> {
        return apartmentRepository.listApartment()
    }
}
