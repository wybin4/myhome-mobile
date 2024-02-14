package com.example.myhome.presentation.mappers

import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.presentation.models.ApartmentUiModel
import com.example.myhome.presentation.models.MeterUiModel
import javax.inject.Inject

class MeterUiMapper @Inject constructor() {
    fun mapApartmentListToUi(apartments: List<ApartmentWithMeterGetModel>): List<ApartmentUiModel> {
        return apartments.map { apartment ->
            ApartmentUiModel(
                id = apartment.id,
                name = apartment.address,
                selected = false
            )
        }
    }

    fun mapMeterListToUi(apartment: ApartmentWithMeterGetModel): List<MeterUiModel> {
        return apartment.meters.map { meter ->
            MeterUiModel(
                id = meter.id,
                factoryNumber = meter.factoryNumber,
                verifiedAt = meter.verifiedAt,
                issuedAt = meter.issuedAt,
                currentReading = meter.currentReading,
                typeOfServiceName = meter.typeOfServiceName,
                unitName = meter.unitName,
                apartmentId = apartment.id,
                address = apartment.address,
                isIssued = false
            )
        }
    }
}