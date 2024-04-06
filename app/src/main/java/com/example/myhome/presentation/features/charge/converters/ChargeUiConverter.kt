package com.example.myhome.presentation.features.charge.converters


import com.example.myhome.features.charge.dtos.ChargeChartListItemResponse
import com.example.myhome.features.charge.dtos.ChargeListItemResponse
import com.example.myhome.features.charge.dtos.DebtListItemResponse
import com.example.myhome.features.charge.dtos.PaymentListItemResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetItemResponse
import com.example.myhome.presentation.features.charge.models.ChargeChartItem
import com.example.myhome.presentation.features.charge.models.ChargeChartModel
import com.example.myhome.presentation.features.charge.models.ChargeUiModel
import com.example.myhome.presentation.features.charge.models.DebtUiModel
import com.example.myhome.presentation.features.charge.models.PaymentUiModel
import com.example.myhome.presentation.features.charge.models.SinglePaymentDocumentUiModel

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

    fun spdToUi(spd: SinglePaymentDocumentGetItemResponse): SinglePaymentDocumentUiModel {
        return SinglePaymentDocumentUiModel(
            id = spd.id,
            amount = spd.amount,
            debt = spd.debt,
            penalty = spd.penalty,
            path = spd.path,
            periodName = formatDate(spd.formatCreatedAt())
        )
    }

    fun chargeToUi(charge: ChargeListItemResponse): ChargeUiModel {
        return charge.run {
            ChargeUiModel(
                id = id,
                apartmentName = apartmentName,
                managementCompanyName = mcName,
                managementCompanyCheckingAccount = mcCheckingAccount,
                createdAt = formatCreatedAt(),
                outstandingDebt = outstandingDebt,
                originalDebt = originalDebt,
                percent = percent,
                amountChange = amountChange
            )
        }
    }

    fun chargeListToDebtUi(charges: List<DebtListItemResponse>): List<DebtUiModel> {
        return charges.filter { it.outstandingDebt > 0 }.map {
            DebtUiModel(
                id = it.id,
                apartmentName = it.apartmentName,
                createdAt = it.formatCreatedAt(),
                outstandingDebt = it.outstandingDebt
            )
        }
    }

    fun chargeListToChart(charges: List<ChargeChartListItemResponse>): List<ChargeChartModel> {
        val apartmentGroups = charges.groupBy { it.apartmentName }
        return apartmentGroups.map { (apartmentName, apartmentCharges) ->
            ChargeChartModel(
                apartmentId = apartmentCharges.first().id,
                apartmentName = apartmentName,
                charges = apartmentCharges.mapIndexed { _, charge ->
                    ChargeChartItem(
                        id = charge.id.toFloat(),
                        createdAt = charge.formatCreatedAt(),
                        amount = charge.amount.toFloat()
                    )
                }
            )
        }
    }

}