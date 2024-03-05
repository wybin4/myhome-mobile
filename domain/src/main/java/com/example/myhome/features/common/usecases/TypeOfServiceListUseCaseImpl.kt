package com.example.myhome.features.common.usecases

import com.example.myhome.features.common.models.TypeOfServiceListItemModel
import com.example.myhome.features.common.repositories.TypeOfServiceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TypeOfServiceListUseCaseImpl @Inject constructor(
    private val typeOfServiceRepository: TypeOfServiceRepository
): TypeOfServiceListUseCase {
    override operator fun invoke(): Flow<List<TypeOfServiceListItemModel>> {
        return typeOfServiceRepository.listTypeOfService()
    }
}
