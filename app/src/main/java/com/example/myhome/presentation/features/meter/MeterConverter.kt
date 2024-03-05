package com.example.myhome.presentation.features.meter

import com.example.myhome.features.meter.models.ApartmentWithMeterListItemModel
import com.example.myhome.features.meter.models.MeterListItemExtendedModel

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

interface MeterUiConverter {
    fun apartmentWithMeterToUi(apartment: ApartmentWithMeterListItemModel): List<MeterUiModel> {
        return apartment.meters.map { meter ->
            MeterUiModel(
                id = meter.id,
                factoryNumber = meter.factoryNumber,
                verifiedAt = meter.verifiedAt,
                issuedAt = meter.issuedAt,
                currentReading = meter.currentReading,
                typeOfServiceName = meter.typeOfServiceName,
                typeOfServiceEngName = meter.typeOfServiceEngName,
                unitName = meter.unitName,
                apartmentId = apartment.id,
                address = apartment.address,
                isIssued = false
            )
        }
    }

    fun meterListToUi(meters: List<MeterListItemExtendedModel>): List<MeterExtendedUiModel> {
        return meters.map { meter ->
            MeterExtendedUiModel(
                id = meter.id,
                subscriberId = meter.subscriberId,
                name = "${meter.address}, ИПУ ${meter.typeOfServiceName}"
            )
        }
    }

    fun apartmentListToUi(apartments: List<ApartmentWithMeterListItemModel>): List<ApartmentUiModel> {
        return apartments.map { apartment ->
            ApartmentUiModel(
                id = apartment.id,
                name = apartment.address,
                selected = false
            )
        }
    }
}