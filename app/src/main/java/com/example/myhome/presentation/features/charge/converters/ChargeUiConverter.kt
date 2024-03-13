package com.example.myhome.presentation.features.charge.converters

import com.example.myhome.features.charge.models.DebtListItemModel
import com.example.myhome.features.charge.models.PaymentListItemModel
import com.example.myhome.features.charge.models.SinglePaymentDocumentGetModel
import com.example.myhome.features.charge.models.SinglePaymentDocumentListItemModel
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
        spds: List<SinglePaymentDocumentListItemModel>,
        debts: List<DebtListItemModel>
    ): List<SpdDebtRelationTextListItem> {
        val sortedSpdList = spds.sortedByDescending { it.createdAt }
        val resultList = mutableListOf<SpdDebtRelationTextListItem>()

        sortedSpdList.map { spd ->
            val currDebt = debts.firstOrNull { it.spdId == spd.id }
            if (currDebt != null) {
                if (currDebt.outstandingDebt > 0) {
                    resultList.add(
                        SpdDebtRelationTextListItem(
                            id = spd.id,
                            outstandingDebt = currDebt.outstandingDebt,
                            createdAt = spd.createdAt,
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

    fun paymentListToUi(payments: List<PaymentListItemModel>): List<PaymentUiModel> {
        return payments.map {
            PaymentUiModel(
                id = it.id,
                amount = it.amount,
                payedAt = it.payedAt
            )
        }
    }

    fun spdToUi(spd: SinglePaymentDocumentGetModel): SinglePaymentDocumentUiModel {
        return SinglePaymentDocumentUiModel(
            id = spd.id,
            amount = spd.amount,
            debt = spd.debt,
            penalty = spd.penalty,
            path = spd.path,
            periodName = formatDate(spd.createdAt)
        )
    }

    fun createSpdGroupedByApartmentList(
        spds: List<SinglePaymentDocumentListItemModel>
    ): List<SpdGroupedByApartmentListItem> {
        val apartmentIdToSpdListMap = mutableMapOf<Int, MutableList<SinglePaymentDocumentListItemModel>>()

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
        spds: List<SinglePaymentDocumentListItemModel>,
        debts: List<DebtListItemModel>
    ): List<SpdDebtRelationGroupedByApartmentListItem> {
        val spdGroupedByApartmentList = createSpdGroupedByApartmentList(spds)

        val spdDebtRelationGroupedList = mutableListOf<SpdDebtRelationGroupedByApartmentListItem>()

        spdGroupedByApartmentList.forEach { spdGroupedByApartment ->
            val apartmentId = spdGroupedByApartment.apartmentId
            val spdList = spdGroupedByApartment.spdList

            val spdDebtRelationList = mutableListOf<SpdDebtRelationListItem>()
            var prevDebt: DebtListItemModel? = null

            spdList.forEach { spd ->
                val currDebt = debts.firstOrNull { it.spdId == spd.id }
                if (currDebt != null) {
                    val amountChange = calculateAmountChange(currDebt, prevDebt)
                    val percent = calculatePercent(currDebt, prevDebt)

                    val spdDebtRelationListItem = SpdDebtRelationListItem(
                        spdId = spd.id,
                        percent = percent,
                        createdAt = spd.createdAt,
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
        spds: List<SinglePaymentDocumentListItemModel>,
        debts: List<DebtListItemModel>
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
                    managementCompanyName = spd.managementCompanyName,
                    periodName = formatDate(spd.createdAt),
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