package com.example.myhome.features.appeal.usecases

import com.example.myhome.features.appeal.AppealRepository
import com.example.myhome.features.appeal.models.AppealListItemModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppealListUseCaseImpl @Inject constructor(
    private val appealRepository: AppealRepository
) : AppealListUseCase {
    override operator fun invoke(): Flow<List<AppealListItemModel>> {
        return appealRepository.listAppeal()
    }

}