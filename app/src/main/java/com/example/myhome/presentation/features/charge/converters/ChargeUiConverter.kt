package com.example.myhome.presentation.features.charge.converters

import com.example.myhome.features.charge.dtos.ChargeListItemResponse
import com.example.myhome.features.charge.dtos.PaymentListItemResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetResponse
import com.example.myhome.presentation.features.charge.models.ChargeChartItem
import com.example.myhome.presentation.features.charge.models.ChargeChartModel
import com.example.myhome.presentation.features.charge.models.ChargeUiModel
import com.example.myhome.presentation.features.charge.models.PaymentUiModel
import com.example.myhome.presentation.features.charge.models.SinglePaymentDocumentUiModel
import com.example.myhome.presentation.features.charge.models.SpdDebtRelationTextListItem

interface ChargeUiConverter: MonthYearConverter {
    fun paymentListToUi(payments: List<PaymentListItemResponse>): List<PaymentUiModel> {
        return payments.map {
            PaymentUiModel(
                id = it.id,
                amount = it.amount,
                payedAt = it.formatPayedAt()
            )
        }
    }

    fun spdToUi(spd: SinglePaymentDocumentGetResponse): SinglePaymentDocumentUiModel {
        return SinglePaymentDocumentUiModel(
            id = spd.id,
            amount = spd.amount,
            debt = spd.debt,
            penalty = spd.penalty,
            path = spd.path,
            periodName = formatDate(spd.formatCreatedAt())
        )
    }

    fun chargeListToUi(charges: List<ChargeListItemResponse>): List<ChargeUiModel> {
        return charges.map {
            ChargeUiModel(
                id = it.id,
                apartmentName = it.apartmentName,
                managementCompanyName = it.mcName,
                managementCompanyCheckingAccount = it.mcCheckingAccount,
                createdAt = it.formatCreatedAt(),
                outstandingDebt = it.outstandingDebt,
                originalDebt = it.originalDebt,
                percent = it.percent,
                amountChange = it.amountChange
            )
        }
    }

    fun chargeListToDebtUi(charges: List<ChargeListItemResponse>): List<SpdDebtRelationTextListItem> {
        return charges.filter { it.outstandingDebt > 0 }.map {
            SpdDebtRelationTextListItem(
                id = it.id,
                apartmentName = it.apartmentName,
                createdAt = it.formatCreatedAt(),
                outstandingDebt = it.outstandingDebt
            )
        }
    }

    fun chargeListToChart(charges: List<ChargeUiModel>): List<ChargeChartModel> {
        val apartmentGroups = charges.groupBy { it.apartmentName }
        return apartmentGroups.map { (apartmentName, apartmentCharges) ->
            ChargeChartModel(
                apartmentId = apartmentCharges.first().id,
                apartmentName = apartmentName,
                charges = apartmentCharges.take(6).mapIndexed { _, charge ->
                    ChargeChartItem(
                        id = charge.id.toFloat(),
                        createdAt = charge.createdAt,
                        amount = charge.originalDebt.toFloat()
                    )
                }
            )
        }
    }

}