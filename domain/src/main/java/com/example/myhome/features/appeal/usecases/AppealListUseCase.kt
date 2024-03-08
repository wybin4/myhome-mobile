package com.example.myhome.features.appeal.usecases

import com.example.myhome.features.appeal.AppealRepository
import com.example.myhome.features.appeal.models.AppealListItemModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppealListUseCase @Inject constructor(
    private val appealRepository: AppealRepository
) {
    operator fun invoke(): Flow<List<AppealListItemModel>> {
        return appealRepository.listAppeal()
    }

}