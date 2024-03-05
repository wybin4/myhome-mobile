package com.example.myhome.features.common.usecases

import com.example.myhome.features.common.models.TypeOfServiceListItemModel
import kotlinx.coroutines.flow.Flow

interface TypeOfServiceListUseCase {
    operator fun invoke(): Flow<List<TypeOfServiceListItemModel>>
}
