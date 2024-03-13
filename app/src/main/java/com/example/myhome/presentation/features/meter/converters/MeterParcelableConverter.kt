package com.example.myhome.presentation.features.meter.converters

import com.example.myhome.presentation.features.meter.MeterGetToScanParcelableModel
import com.example.myhome.presentation.features.meter.MeterGetToUpdateParcelableModel
import com.example.myhome.presentation.features.meter.MeterListToGetParcelableModel
import com.example.myhome.presentation.features.meter.MeterUiModel

interface MeterParcelableConverter {
    fun meterUiToGetParcel(meter: MeterUiModel): MeterListToGetParcelableModel {
        return MeterListToGetParcelableModel(
            id = meter.id,
            factoryNumber = meter.factoryNumber,
            verifiedAt = meter.verifiedAt,
            issuedAt = meter.issuedAt,
            isIssued = meter.isIssued,
            apartmentId = meter.apartmentId,
            address = meter.address,
            currentReading = meter.currentReading,
            typeOfServiceName = meter.typeOfServiceName,
            typeOfServiceEngName = meter.typeOfServiceEngName,
            unitName = meter.unitName
        )
    }

    fun meterGetToScanParcel(meter: MeterListToGetParcelableModel, prevReading: Double): MeterGetToScanParcelableModel {
        return MeterGetToScanParcelableModel(
            meterId = meter.id,
            address = meter.address,
            previousReading = prevReading,
            typeOfServiceName = meter.typeOfServiceName,
            typeOfServiceEngName = meter.typeOfServiceEngName,
            unitName = meter.unitName
        )
    }

    fun meterGetToUpdateParcel(meter: MeterListToGetParcelableModel): MeterGetToUpdateParcelableModel {
        return MeterGetToUpdateParcelableModel(
            meterId = meter.id,
            meterName = meter.address + ", " + meter.typeOfServiceName,
            apartmentId = meter.apartmentId
        )
    }
}