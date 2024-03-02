package com.example.myhome.testutils.mocks.appeal.usecases

import com.example.myhome.appeal.models.AppealGetModel
import com.example.myhome.appeal.usecases.AppealListUseCase
import kotlinx.coroutines.flow.Flow

class AppealListUseCaseTest : AppealListUseCase {
    override fun invoke(): Flow<List<AppealGetModel>> {
        TODO("Not yet implemented")
    }

}
