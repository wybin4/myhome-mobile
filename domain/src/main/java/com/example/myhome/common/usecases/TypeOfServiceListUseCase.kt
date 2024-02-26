package com.example.myhome.common.usecases

import com.example.myhome.common.models.TypeOfServiceGetModel
import kotlinx.coroutines.flow.Flow

interface TypeOfServiceListUseCase {
    operator fun invoke(): Flow<List<TypeOfServiceGetModel>>
}
