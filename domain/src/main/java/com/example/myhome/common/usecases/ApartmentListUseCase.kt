package com.example.myhome.common.usecases

import com.example.myhome.common.models.ApartmentGetModel
import com.example.myhome.common.repositories.ApartmentRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ApartmentListUseCase @Inject constructor(
    private val apartmentRepository: ApartmentRepository
) {
    operator fun invoke(): Flow<List<ApartmentGetModel>> {
        return apartmentRepository.listApartment()
    }
}
