package com.example.myhome.testutils.mocks.appeal.usecases

import com.example.myhome.features.appeal.models.AppealListItemModel
import com.example.myhome.features.appeal.usecases.AppealListUseCase
import kotlinx.coroutines.flow.Flow

class AppealListUseCaseTest : AppealListUseCase {
    override fun invoke(): Flow<List<AppealListItemModel>> {
        TODO("Not yet implemented")
    }

}
