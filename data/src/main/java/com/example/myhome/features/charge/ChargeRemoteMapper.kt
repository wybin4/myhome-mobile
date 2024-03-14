package com.example.myhome.features.charge

import com.example.myhome.features.charge.dtos.DebtListItemResponse
import com.example.myhome.features.charge.dtos.PaymentListItemResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentListItemResponse
import com.example.myhome.features.charge.models.DebtListItemModel
import com.example.myhome.features.charge.models.PaymentListItemModel
import com.example.myhome.features.charge.models.SinglePaymentDocumentGetModel
import com.example.myhome.features.charge.models.SinglePaymentDocumentListItemModel

class ChargeRemoteMapper {
    fun mapSinglePaymentDocumentListToDomain(
        spds: List<SinglePaymentDocumentListItemResponse>
    ): List<SinglePaymentDocumentListItemModel> {
        return spds.map {
            SinglePaymentDocumentListItemModel(
                id = it.id,
                apartmentName = it.apartmentName,
                apartmentId = it.apartmentId,
                managementCompanyName = it.mcName,
                managementCompanyCheckingAccount = it.mcCheckingAccount,
                createdAt = it.formatCreatedAt()
            )
        }
    }

    fun mapSinglePaymentDocumentGetToDomain(spd: SinglePaymentDocumentGetResponse): SinglePaymentDocumentGetModel {
        return spd.run {
            SinglePaymentDocumentGetModel(
                id = id,
                amount = amount,
                debt = debt,
                penalty = penalty,
                path = path,
                createdAt = formatCreatedAt()
            )
        }
    }

    fun mapPaymentListToDomain(payments: List<PaymentListItemResponse>): List<PaymentListItemModel> {
        return payments.map {
            PaymentListItemModel(
                id = it.id,
                amount = it.amount,
                payedAt = it.formatPayedAt()
            )
        }
    }

    fun mapDebtListToDomain(debts: List<DebtListItemResponse>): List<DebtListItemModel> {
        return debts.map {
            DebtListItemModel(
                spdId = it.singlePaymentDocumentId,
                outstandingDebt = it.outstandingDebt,
                originalDebt = it.originalDebt
            )
        }
    }
}
