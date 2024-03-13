package com.example.myhome.features.charge.usecases

import com.example.myhome.features.charge.ChargeRepository
import com.example.myhome.features.charge.models.DebtListItemModel
import com.example.myhome.features.charge.models.SinglePaymentDocumentListItemModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class ChargeListUseCase @Inject constructor(
    private val chargeRepository: ChargeRepository
) {
    operator fun invoke(): Flow<Pair<List<SinglePaymentDocumentListItemModel>, List<DebtListItemModel>>> {
        val debts = chargeRepository.listDebt()
        val spds = chargeRepository.listSinglePaymentDocument()
        return spds.combine(debts) { spdsList, debtsList ->
            Pair(spdsList, debtsList)
        }
    }
}
