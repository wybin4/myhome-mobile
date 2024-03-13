package com.example.myhome.features.charge

import com.example.myhome.features.charge.models.DebtListItemModel
import com.example.myhome.features.charge.models.PaymentListItemModel
import com.example.myhome.features.charge.models.SinglePaymentDocumentGetModel
import com.example.myhome.features.charge.models.SinglePaymentDocumentListItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class  ChargeRepositoryImpl(
    private val chargeStorage: ChargeStorage,
    private val chargeRemoteMapper: ChargeRemoteMapper
): ChargeRepository {
    override fun listSinglePaymentDocument(): Flow<List<SinglePaymentDocumentListItemModel>> = flow {
        val result = chargeStorage.listSinglePaymentDocument()
        emit(chargeRemoteMapper.mapSinglePaymentDocumentListToDomain(result))
    }

    override fun getSinglePaymentDocument(id: Int): Flow<SinglePaymentDocumentGetModel> = flow {
        val result = chargeStorage.getSinglePaymentDocument(id)
        emit(chargeRemoteMapper.mapSinglePaymentDocumentGetToDomain(result))
    }

    override fun listDebt(): Flow<List<DebtListItemModel>> = flow {
        val result = chargeStorage.listDebt()
        emit(chargeRemoteMapper.mapDebtListToDomain(result))
    }

    override fun listPayment(spdId: Int): Flow<List<PaymentListItemModel>> = flow {
        val result = chargeStorage.listPayment(spdId)
        emit(chargeRemoteMapper.mapPaymentListToDomain(result))
    }

}
