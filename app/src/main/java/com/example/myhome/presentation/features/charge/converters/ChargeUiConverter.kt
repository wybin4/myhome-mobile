package com.example.myhome.presentation.features.charge.converters

import com.example.myhome.features.charge.dtos.DebtListItemResponse
import com.example.myhome.features.charge.dtos.PaymentListItemResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentGetResponse
import com.example.myhome.features.charge.dtos.SinglePaymentDocumentListItemResponse
import com.example.myhome.presentation.features.charge.ChargeCalculator
import com.example.myhome.presentation.features.charge.models.ChargeUiModel
import com.example.myhome.presentation.features.charge.models.PaymentUiModel
import com.example.myhome.presentation.features.charge.models.SinglePaymentDocumentUiModel
import com.example.myhome.presentation.features.charge.models.SpdDebtRelationGroupedByApartmentListItem
import com.example.myhome.presentation.features.charge.models.SpdDebtRelationListItem
import com.example.myhome.presentation.features.charge.models.SpdDebtRelationTextListItem
import com.example.myhome.presentation.features.charge.models.SpdGroupedByApartmentListItem

interface ChargeUiConverter: MonthYearConverter, ChargeCalculator {
    fun spdDebtRelationTextListToUi(
        spds: List<SinglePaymentDocumentListItemResponse>,
        debts: List<DebtListItemResponse>
    ): List<SpdDebtRelationTextListItem> {
        val sortedSpdList = spds.sortedByDescending { it.createdAt }
        val resultList = mutableListOf<SpdDebtRelationTextListItem>()

        sortedSpdList.map { spd ->
            val currDebt = debts.firstOrNull { it.singlePaymentDocumentId == spd.id }
            if (currDebt != null) {
                if (currDebt.outstandingDebt > 0) {
                    resultList.add(
                        SpdDebtRelationTextListItem(
                            id = spd.id,
                            outstandingDebt = currDebt.outstandingDebt,
                            createdAt = spd.formatCreatedAt(),
                            apartmentName = spd.apartmentName
                        )
                    )
                }
            } else {
                throw IllegalArgumentException("No corresponding debt found for SPD with id ${spd.id}")
            }
        }
        return resultList
    }

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

    fun createSpdGroupedByApartmentList(
        spds: List<SinglePaymentDocumentListItemResponse>
    ): List<SpdGroupedByApartmentListItem> {
        val apartmentIdToSpdListMap = mutableMapOf<Int, MutableList<SinglePaymentDocumentListItemResponse>>()

        spds.forEach { spd ->
            val apartmentId = spd.apartmentId
            val spdList = apartmentIdToSpdListMap.getOrPut(apartmentId) { mutableListOf() }
            spdList.add(spd)
        }

        apartmentIdToSpdListMap.values.forEach { spdList ->
            spdList.sortBy { it.createdAt }
        }

        return apartmentIdToSpdListMap.map { (apartmentId, spdList) ->
            SpdGroupedByApartmentListItem(apartmentId, spdList)
        }
    }

    fun createSpdDebtRelationGroupedByApartmentList(
        spds: List<SinglePaymentDocumentListItemResponse>,
        debts: List<DebtListItemResponse>
    ): List<SpdDebtRelationGroupedByApartmentListItem> {
        val spdGroupedByApartmentList = createSpdGroupedByApartmentList(spds)

        val spdDebtRelationGroupedList = mutableListOf<SpdDebtRelationGroupedByApartmentListItem>()

        spdGroupedByApartmentList.forEach { spdGroupedByApartment ->
            val apartmentId = spdGroupedByApartment.apartmentId
            val spdList = spdGroupedByApartment.spdList

            val spdDebtRelationList = mutableListOf<SpdDebtRelationListItem>()
            var prevDebt: DebtListItemResponse? = null

            spdList.forEach { spd ->
                val currDebt = debts.firstOrNull { it.singlePaymentDocumentId == spd.id }
                if (currDebt != null) {
                    val amountChange = calculateAmountChange(currDebt, prevDebt)
                    val percent = calculatePercent(currDebt, prevDebt)

                    val spdDebtRelationListItem = SpdDebtRelationListItem(
                        spdId = spd.id,
                        percent = percent,
                        createdAt = spd.formatCreatedAt(),
                        amountChange = amountChange,
                        originalDebt = currDebt.originalDebt,
                        outstandingDebt = currDebt.outstandingDebt
                    )
                    spdDebtRelationList.add(spdDebtRelationListItem)

                    prevDebt = currDebt
                } else {
                    throw IllegalArgumentException("No corresponding spdDebtRelation found for SPD with id ${spd.id}")
                }
            }
            val spdDebtRelationGroupedItem = SpdDebtRelationGroupedByApartmentListItem(apartmentId, spdDebtRelationList)
            spdDebtRelationGroupedList.add(spdDebtRelationGroupedItem)
        }

        return spdDebtRelationGroupedList
    }

    fun chargeListToUi(
        spds: List<SinglePaymentDocumentListItemResponse>,
        debts: List<DebtListItemResponse>
    ): List<ChargeUiModel> {
        val spdDebtRelationGroupedByApartmentList = createSpdDebtRelationGroupedByApartmentList(spds, debts)
        val sortedByDescendingSpdList = spds.sortedByDescending { it.createdAt }
        val chargeUiModels = mutableListOf<ChargeUiModel>()

        sortedByDescendingSpdList.forEach { spd ->
            val spdDebtRelations = spdDebtRelationGroupedByApartmentList.flatMap { it.spdDebtRelationList }
                .filter { it.spdId == spd.id }

            if (spdDebtRelations.isNotEmpty()) {
                val spdDebtRelation = spdDebtRelations.first()
                val chargeUiModel = ChargeUiModel(
                    id = spd.id,
                    apartmentName = spd.apartmentName,
                    managementCompanyName = spd.mcName,
                    managementCompanyCheckingAccount = spd.mcCheckingAccount,
                    periodName = formatDate(spd.formatCreatedAt()),
                    percent = spdDebtRelation.percent,
                    amountChange = spdDebtRelation.amountChange,
                    outstandingDebt = spdDebtRelation.outstandingDebt,
                    originalDebt = spdDebtRelation.originalDebt
                )
                chargeUiModels.add(chargeUiModel)
            } else {
                throw IllegalArgumentException("No corresponding spdDebtRelation found for SPD with id ${spd.id}")
            }
        }

        return chargeUiModels
    }

}