package com.example.myhome.features.meter.mappers

import com.example.myhome.features.meter.dtos.ApartmentWithMeterListItemResponse
import com.example.myhome.features.meter.dtos.MeterListItemResponseExtended
import com.example.myhome.features.meter.models.ApartmentWithMeterListItemModel
import com.example.myhome.features.meter.models.MeterListItemExtendedModel
import com.example.myhome.features.meter.models.MeterListItemModel

class MeterRemoteMapper {
    fun mapApartmentWithMeterListToDomain(apartments: List<ApartmentWithMeterListItemResponse>): List<ApartmentWithMeterListItemModel> {
        return apartments.map { apartment ->
            val meters = apartment.meters.map {
                MeterListItemModel(
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
            ApartmentWithMeterListItemModel(
                id = apartment.apartmentId,
                address = apartment.apartmentFullAddress,
                meters = meters
            )
        }
    }

    fun mapMeterListToDomain(meters: List<MeterListItemResponseExtended>): List<MeterListItemExtendedModel> {
        return meters.map {
            MeterListItemExtendedModel(
                id = it.id,
                typeOfServiceName = it.typeOfServiceName,
                subscriberId = it.subscriberId,
                address = it.address
            )
        }
    }
}
