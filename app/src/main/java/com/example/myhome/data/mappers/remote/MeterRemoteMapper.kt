package com.example.myhome.data.mappers.remote

import com.example.myhome.data.dtos.MeterAddDto
import com.example.myhome.data.dtos.MeterGetDto
import com.example.myhome.data.dtos.MeterUpdateDto
import com.example.myhome.data.mappers.DateMapper
import com.example.myhome.domain.models.MeterAddModel
import com.example.myhome.domain.models.MeterGetModel
import com.example.myhome.domain.models.MeterUpdateModel

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
