package com.example.myhome.data.meter.mappers

import com.example.myhome.data.base.mappers.DateMapper
import com.example.myhome.data.meter.dtos.MeterAddDto
import com.example.myhome.data.meter.dtos.MeterGetDto
import com.example.myhome.data.meter.dtos.MeterUpdateDto
import com.example.myhome.domain.meter.models.MeterAddModel
import com.example.myhome.domain.meter.models.MeterGetModel
import com.example.myhome.domain.meter.models.MeterUpdateModel

class MeterRemoteMapper(private val dateMapper: DateMapper) {
    fun mapAddToRemote(meter: MeterAddModel): MeterAddDto {
        return meter.run {
            MeterAddDto(
                id = id,
                factoryNumber = factoryNumber,
                verifiedAt = verifiedAt.toString(),
                issuedAt = issuedAt.toString(),
                apartmentId = apartmentId,
                typeOfServiceId = typeOfServiceId,
                previousReading = previousReading,
                previousReadAt = previousReadAt?.toString()
            )
        }
    }

    fun mapUpdateToRemote(meter: MeterUpdateModel): MeterUpdateDto {
        return meter.run {
            MeterUpdateDto(
                id = id,
                verifiedAt = verifiedAt.toString(),
                issuedAt = issuedAt.toString()
            )
        }
    }

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

    fun mapToDomain(meter: MeterGetDto): MeterGetModel {
        return meter.run {
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
