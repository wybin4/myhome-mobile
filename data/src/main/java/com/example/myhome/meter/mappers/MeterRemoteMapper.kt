package com.example.myhome.meter.mappers

import com.example.myhome.base.mappers.DateMapper
import com.example.myhome.meter.dtos.ApartmentWithMeterGetDto
import com.example.myhome.meter.models.ApartmentWithMeterGetModel
import com.example.myhome.meter.models.MeterGetModel

class MeterRemoteMapper(private val dateMapper: DateMapper) {
    fun mapListToDomain(apartments: List<ApartmentWithMeterGetDto>): List<ApartmentWithMeterGetModel> {
        return apartments.map { apartment ->
            val meters = apartment.meters.map {
                MeterGetModel(
                    id = it.id,
                    factoryNumber = it.factoryNumber,
                    verifiedAt = dateMapper.mapyyyyMMdd(it.verifiedAt),
                    issuedAt = dateMapper.mapyyyyMMdd(it.issuedAt),
                    typeOfServiceId = it.typeOfServiceId,
                    currentReading = it.currentReading,
                    typeOfServiceName = it.typeOfServiceName,
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
}
