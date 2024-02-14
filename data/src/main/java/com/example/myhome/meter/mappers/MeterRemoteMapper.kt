package com.example.myhome.meter.mappers

import com.example.myhome.base.mappers.DateMapper
import com.example.myhome.meter.dtos.MeterGetDto
import com.example.myhome.meter.models.MeterGetModel

class MeterRemoteMapper(private val dateMapper: DateMapper) {
    fun mapListToDomain(meters: List<MeterGetDto>): List<MeterGetModel> {
        return meters.map { meter ->
                meter.run {
                    MeterGetModel(
                        id = id,
                        factoryNumber = factoryNumber,
                        verifiedAt = dateMapper.mapyyyyMMdd(verifiedAt),
                        issuedAt = dateMapper.mapyyyyMMdd(issuedAt),
                        apartmentId = apartmentId,
                        typeOfServiceId = typeOfServiceId,
                        currentReading = currentReading,
                        typeOfServiceName = typeOfServiceName,
                        unitName = unitName
                    )
                }
            }
    }
}
