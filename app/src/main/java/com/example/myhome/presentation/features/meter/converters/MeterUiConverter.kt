package com.example.myhome.presentation.features.meter.converters

import com.example.myhome.features.meter.dtos.ApartmentWithMeterListItemResponse
import com.example.myhome.features.meter.dtos.MeterExtendedListItemResponse
import com.example.myhome.presentation.features.meter.ApartmentUiModel
import com.example.myhome.presentation.features.meter.MeterExtendedUiModel
import com.example.myhome.presentation.features.meter.MeterUiModel

interface MeterUiConverter {
    fun apartmentWithMeterToUi(apartment: ApartmentWithMeterListItemResponse): List<MeterUiModel> {
        return apartment.meters.map { meter ->
            MeterUiModel(
                id = meter.id,
                factoryNumber = meter.factoryNumber,
                verifiedAt = meter.formatVerifiedAt(),
                issuedAt = meter.formatIssuedAt(),
                currentReading = meter.currentReading,
                typeOfServiceName = meter.typeOfServiceName,
                typeOfServiceEngName = meter.typeOfServiceEngName,
                unitName = meter.unitName,
                apartmentId = apartment.apartmentId,
                address = apartment.apartmentFullAddress,
                isIssued = false
            )
        }
    }

    fun meterListToUi(meters: List<MeterExtendedListItemResponse>): List<MeterExtendedUiModel> {
        return meters.map { meter ->
            MeterExtendedUiModel(
                id = meter.id,
                subscriberId = meter.subscriberId,
                name = "${meter.address}, ИПУ ${meter.typeOfServiceName}"
            )
        }
    }

    fun apartmentListToUi(apartments: List<ApartmentWithMeterListItemResponse>): List<ApartmentUiModel> {
        return apartments.map { apartment ->
            ApartmentUiModel(
                id = apartment.apartmentId,
                name = apartment.apartmentFullAddress,
                selected = false
            )
        }
    }
}