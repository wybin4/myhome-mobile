package com.example.myhome.testutils.mocks.appeal

import com.example.myhome.features.appeal.AppealRepository
import com.example.myhome.features.appeal.models.AppealAddMeterModel
import com.example.myhome.features.appeal.models.AppealListItemModel
import com.example.myhome.features.appeal.models.AppealProblemOrClaimModel
import com.example.myhome.features.appeal.models.AppealUpdateMeterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AppealRepositoryTest : AppealRepository {
    override fun addMeter(appeal: AppealAddMeterModel): Flow<Boolean> {
        return flowOf(true)
    }

    override fun updateMeter(appeal: AppealUpdateMeterModel): Flow<Boolean> {
        return flowOf(true)
    }

    override fun problem(appeal: AppealProblemOrClaimModel): Flow<Boolean> {
        return flowOf(true)
    }

    override fun claim(appeal: AppealProblemOrClaimModel): Flow<Boolean> {
        return flowOf(true)
    }

    override fun listAppeal(): Flow<List<AppealListItemModel>> {
        TODO("Not yet implemented")
    }
}
