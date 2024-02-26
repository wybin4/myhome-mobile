package com.example.myhome.common.usecases

import com.example.myhome.common.models.ApartmentGetModel
import kotlinx.coroutines.flow.Flow

interface ApartmentListUseCase {
    operator fun invoke(): Flow<List<ApartmentGetModel>>
}
