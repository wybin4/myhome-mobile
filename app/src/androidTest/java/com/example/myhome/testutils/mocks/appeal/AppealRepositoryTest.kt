package com.example.myhome.testutils.mocks.appeal

import androidx.paging.PagingData
import com.example.myhome.features.appeal.AppealAddMeterAddRequest
import com.example.myhome.features.appeal.AppealClaimAddRequest
import com.example.myhome.features.appeal.AppealListItemResponse
import com.example.myhome.features.appeal.AppealProblemAddRequest
import com.example.myhome.features.appeal.AppealVerifyMeterAddRequest
import com.example.myhome.features.appeal.repositories.AppealRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class AppealRepositoryTest : AppealRepository {
    override fun addMeter(appeal: AppealAddMeterAddRequest): Flow<Boolean> {
        return flowOf(true)
    }

    override fun updateMeter(appeal: AppealVerifyMeterAddRequest): Flow<Boolean> {
        return flowOf(true)
    }

    override fun problem(appeal: AppealProblemAddRequest): Flow<Boolean> {
        return flowOf(true)
    }

    override fun claim(appeal: AppealClaimAddRequest): Flow<Boolean> {
        return flowOf(true)
    }

    override fun listAppeal(): Flow<PagingData<AppealListItemResponse>> {
        TODO("Not yet implemented")
    }
}
