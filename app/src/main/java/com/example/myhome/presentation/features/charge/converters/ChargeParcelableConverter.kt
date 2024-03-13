package com.example.myhome.presentation.features.charge.converters

import com.example.myhome.presentation.features.charge.models.ChargeListToGetParcelableModel
import com.example.myhome.presentation.features.charge.models.ChargeUiModel

interface ChargeParcelableConverter {
    fun chargeUiToGetParcel(charge: ChargeUiModel): ChargeListToGetParcelableModel {
        return ChargeListToGetParcelableModel(
            id = charge.id,
            managementCompanyName = charge.managementCompanyName,
            apartmentName = charge.apartmentName,
            periodName = charge.periodName,
            originalDebt = charge.originalDebt,
            outstandingDebt = charge.outstandingDebt
        )
    }
}