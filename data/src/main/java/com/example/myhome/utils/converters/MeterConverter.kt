package com.example.myhome.utils.converters

import com.example.myhome.meter.dtos.ApartmentWithMeterGetDto
import com.example.myhome.meter.dtos.MeterListItemDto
import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.meter.models.MeterGetModel
import com.example.myhome.meter.models.MeterListItemModel

interface MeterConverter {
    fun apartmentWithMeterListToDomain(apartments: List<ApartmentWithMeterGetDto>): List<ApartmentWithMeterGetModel> {
        return apartments.map { apartment ->
            val meters = apartment.meters.map {
                MeterGetModel(
                    id = it.id,
                    factoryNumber = it.factoryNumber,
                    verifiedAt = it.formatVerifiedAt(),
                    issuedAt = it.formatIssuedAt(),
                    typeOfServiceId = it.typeOfServiceId,
                    currentReading = it.currentReading,
                    typeOfServiceName = it.typeOfServiceName,
                    typeOfServiceEngName = it.typeOfServiceEngName,
                    unitName = it.unitName
                )
            }
            ApartmentWithMeterGetModel(
                id = apartment.apartmentId,
                address = apartment.apartmentFullAddress,
                meters = meters
            )
        }
    }

    fun meterListToDomain(meters: List<MeterListItemDto>): List<MeterListItemModel> {
        return meters.map {
            MeterListItemModel(
                id = it.id,
                typeOfServiceName = it.typeOfServiceName,
                subscriberId = it.subscriberId,
                address = it.address
            )
        }
    }

}
