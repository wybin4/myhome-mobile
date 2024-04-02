package com.example.myhome.features.appeal.repositories

import android.graphics.Bitmap
import androidx.paging.PagingData
import com.example.myhome.features.appeal.AppealAddMeterAddRequest
import com.example.myhome.features.appeal.AppealClaimAddRequest
import com.example.myhome.features.appeal.AppealListItemResponse
import com.example.myhome.features.appeal.AppealProblemAddRequest
import com.example.myhome.features.appeal.AppealVerifyMeterAddRequest
import com.example.myhome.models.FilterListItemRequest
import kotlinx.coroutines.flow.Flow

interface AppealRepository {
    fun addMeter(appeal: AppealAddMeterAddRequest, file: Bitmap): Flow<Boolean>
    fun updateMeter(appeal: AppealVerifyMeterAddRequest, file: Bitmap): Flow<Boolean>
    fun problem(appeal: AppealProblemAddRequest): Flow<Boolean>
    fun claim(appeal: AppealClaimAddRequest): Flow<Boolean>
    fun listAppeal(filters: List<FilterListItemRequest>? = null): Flow<PagingData<AppealListItemResponse>>
}