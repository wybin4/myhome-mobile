package com.example.myhome.appeal.usecases

import com.example.myhome.appeal.models.AppealGetModel
import com.example.myhome.appeal.repositories.AppealRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppealListUseCaseImpl @Inject constructor(
    private val appealRepository: AppealRepository
) : AppealListUseCase {
    override operator fun invoke(): Flow<List<AppealGetModel>> {
        return appealRepository.listAppeal()
    }

}