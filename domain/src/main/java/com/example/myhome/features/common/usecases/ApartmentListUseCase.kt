package com.example.myhome.features.common.usecases

import com.example.myhome.features.common.models.ApartmentListItemModel
import kotlinx.coroutines.flow.Flow

interface ApartmentListUseCase {
    operator fun invoke(): Flow<List<ApartmentListItemModel>>
}
