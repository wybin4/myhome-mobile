package com.example.myhome.features.appeal.usecases

import com.example.myhome.features.appeal.models.AppealListItemModel
import kotlinx.coroutines.flow.Flow

interface AppealListUseCase {
    operator fun invoke(): Flow<List<AppealListItemModel>>
}