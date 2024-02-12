package com.example.myhome.meter.mappers

import com.example.myhome.appeal.dtos.AddIndividualMeterData
import com.example.myhome.appeal.dtos.VerifyIndividualMeterData
import com.example.myhome.base.mappers.DateMapper
import com.example.myhome.meter.dtos.MeterAddDto
import com.example.myhome.meter.dtos.MeterGetDto
import com.example.myhome.meter.dtos.MeterUpdateDto
import com.example.myhome.meter.models.MeterAddModel
import com.example.myhome.meter.models.MeterGetModel
import com.example.myhome.meter.models.MeterUpdateModel

class MeterRemoteMapper(private val dateMapper: DateMapper) {
    fun mapAddToRemote(meter: MeterAddModel): MeterAddDto {
        val data = meter.run {
            AddIndividualMeterData(
                 typeOfServiceId = typeOfServiceId,
                 apartmentId = apartmentId,
                 factoryNumber = factoryNumber,
                 issuedAt = issuedAt.toString(),
                 verifiedAt = verifiedAt.toString(),
                 attachment = "/path/to"
            )
        }

        return meter.run {
            MeterAddDto(
                managementCompanyId = managementCompanyId,
                subscriberId = subscriberId,
                data = data
            )
        }
    }

    fun mapUpdateToRemote(meter: MeterUpdateModel): MeterUpdateDto {
        val data = meter.run {
            VerifyIndividualMeterData(
                meterId = id,
                issuedAt = issuedAt.toString(),
                verifiedAt = verifiedAt.toString(),
                attachment = "/path/to"
            )
        }

        return meter.run {
            MeterUpdateDto(
                managementCompanyId = managementCompanyId,
                subscriberId = subscriberId,
                data = data
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
