package com.example.myhome.features.charge.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.myhome.features.charge.ChargePagingSource
import com.example.myhome.features.charge.ChargeStorage
import com.example.myhome.features.charge.dtos.ChargeChartListItemResponse
import com.example.myhome.features.charge.dtos.ChargeListItemResponse
import com.example.myhome.features.charge.dtos.DebtListItemResponse
import com.example.myhome.features.charge.dtos.PaymentListItemResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetItemResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class  ChargeRepositoryImpl(
    private val chargeStorage: ChargeStorage,
    private val chargePagingSource: ChargePagingSource,
    private val pageConfig: PagingConfig
): ChargeRepository {
    override fun listCharge(): Flow<PagingData<ChargeListItemResponse>> {
        return Pager(
            config = pageConfig,
            pagingSourceFactory = { chargePagingSource }
        ).flow
    }

    override fun listDebt(): Flow<List<DebtListItemResponse>> = flow {
        emit(chargeStorage.listDebt())
    }

    override fun listChargeChart(): Flow<List<ChargeChartListItemResponse>> = flow {
        emit(chargeStorage.listChargeChart())
    }

    override fun getSinglePaymentDocument(id: Int): Flow<SinglePaymentDocumentGetItemResponse> = flow {
        emit(chargeStorage.getSinglePaymentDocument(id))
    }

    override fun listPayment(spdId: Int): Flow<List<PaymentListItemResponse>> = flow {
        emit(chargeStorage.listPayment(spdId))
    }

}
