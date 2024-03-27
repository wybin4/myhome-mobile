package com.example.myhome.features.appeal.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myhome.ConstantsData.Companion.PAGE_SIZE
import com.example.myhome.features.appeal.AppealAddMeterAddRequest
import com.example.myhome.features.appeal.AppealListItemResponse
import com.example.myhome.features.appeal.AppealClaimAddRequest
import com.example.myhome.features.appeal.AppealPagingSource
import com.example.myhome.features.appeal.AppealProblemAddRequest
import com.example.myhome.features.appeal.AppealStorage
import com.example.myhome.features.appeal.AppealVerifyMeterAddRequest
import com.example.myhome.features.event.EventApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class AppealRepositoryImpl(
    private val appealStorage: AppealStorage,
    private val eventApiService: EventApiService
): AppealRepository {

    override fun addMeter(appeal: AppealAddMeterAddRequest): Flow<Boolean> = flow {
        emit(appealStorage.addAppeal(appeal))
    }

    override fun updateMeter(appeal: AppealVerifyMeterAddRequest): Flow<Boolean> = flow {
        emit(appealStorage.addAppeal(appeal))
    }

    override fun problem(appeal: AppealProblemAddRequest): Flow<Boolean> = flow {
        emit(appealStorage.addAppeal(appeal))
    }

    override fun claim(appeal: AppealClaimAddRequest): Flow<Boolean> = flow {
        emit(appealStorage.addAppeal(appeal))
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun listAppeal(): Flow<PagingData<AppealListItemResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE
            ),
            pagingSourceFactory = { AppealPagingSource(eventApiService) }
        ).flow
    }

}