package com.example.myhome.appeal.mappers

import com.example.myhome.appeal.dtos.AddIndividualMeterData
import com.example.myhome.appeal.dtos.VerifyIndividualMeterData
import com.example.myhome.appeal.models.AppealAddMeterModel
import com.example.myhome.appeal.models.AppealUpdateMeterModel
import com.example.myhome.meter.dtos.MeterAddDto
import com.example.myhome.meter.dtos.MeterUpdateDto

class AppealRemoteMapper {
    fun mapAddToRemote(meter: AppealAddMeterModel): MeterAddDto {
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

    fun mapUpdateToRemote(meter: AppealUpdateMeterModel): MeterUpdateDto {
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
}