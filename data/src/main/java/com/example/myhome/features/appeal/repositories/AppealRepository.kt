package com.example.myhome.features.appeal.repositories

import androidx.paging.PagingData
import com.example.myhome.features.appeal.AppealAddMeterAddRequest
import com.example.myhome.features.appeal.AppealListItemResponse
import com.example.myhome.features.appeal.AppealClaimAddRequest
import com.example.myhome.features.appeal.AppealProblemAddRequest
import com.example.myhome.features.appeal.AppealVerifyMeterAddRequest
import kotlinx.coroutines.flow.Flow

interface AppealRepository {
    fun addMeter(appeal: AppealAddMeterAddRequest): Flow<Boolean>
    fun updateMeter(appeal: AppealVerifyMeterAddRequest): Flow<Boolean>
    fun problem(appeal: AppealProblemAddRequest): Flow<Boolean>
    fun claim(appeal: AppealClaimAddRequest): Flow<Boolean>
    fun listAppeal(): Flow<PagingData<AppealListItemResponse>>
}