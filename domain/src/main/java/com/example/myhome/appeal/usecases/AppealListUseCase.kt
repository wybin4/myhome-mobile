package com.example.myhome.appeal.usecases

import com.example.myhome.appeal.models.AppealGetModel
import kotlinx.coroutines.flow.Flow

interface AppealListUseCase {
    operator fun invoke(): Flow<List<AppealGetModel>>
}