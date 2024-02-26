package com.example.myhome.common.usecases

import com.example.myhome.common.models.TypeOfServiceGetModel
import com.example.myhome.common.repositories.TypeOfServiceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TypeOfServiceListUseCaseImpl @Inject constructor(
    private val typeOfServiceRepository: TypeOfServiceRepository
): TypeOfServiceListUseCase {
    override operator fun invoke(): Flow<List<TypeOfServiceGetModel>> {
        return typeOfServiceRepository.listTypeOfService()
    }
}
