package com.example.myhome.presentation.features.meter.converters

import com.example.myhome.features.meter.models.ApartmentWithMeterListItemModel
import com.example.myhome.features.meter.models.MeterListItemExtendedModel
import com.example.myhome.presentation.features.meter.ApartmentUiModel
import com.example.myhome.presentation.features.meter.MeterExtendedUiModel
import com.example.myhome.presentation.features.meter.MeterUiModel

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