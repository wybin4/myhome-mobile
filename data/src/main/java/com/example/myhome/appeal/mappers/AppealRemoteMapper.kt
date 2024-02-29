package com.example.myhome.appeal.mappers

import com.example.myhome.appeal.dtos.AddIndividualMeterData
import com.example.myhome.appeal.dtos.AppealGetDto
import com.example.myhome.appeal.dtos.ClaimDto
import com.example.myhome.appeal.dtos.ProblemDto
import com.example.myhome.appeal.dtos.ProblemOrClaimData
import com.example.myhome.appeal.dtos.VerifyIndividualMeterData
import com.example.myhome.appeal.models.AppealAddMeterModel
import com.example.myhome.appeal.models.AppealGetModel
import com.example.myhome.appeal.models.AppealProblemOrClaimModel
import com.example.myhome.appeal.models.AppealStatus
import com.example.myhome.appeal.models.AppealType
import com.example.myhome.appeal.models.AppealUpdateMeterModel
import com.example.myhome.meter.dtos.MeterAddDto
import com.example.myhome.meter.dtos.MeterUpdateDto
import java.util.Date

class AppealRemoteMapper {
    fun mapAddToRemote(meter: AppealAddMeterModel): MeterAddDto {
        val data = meter.run {
            AddIndividualMeterData(
                typeOfServiceId = typeOfServiceId,
                apartmentId = apartmentId,
                factoryNumber = factoryNumber,
                issuedAt = issuedAt.toString(),
                verifiedAt = verifiedAt.toString(),
                attachment = attachment
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
                meterId = meterId,
                issuedAt = issuedAt.toString(),
                verifiedAt = verifiedAt.toString(),
                attachment = attachment
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

    fun mapProblemToRemote(meter: AppealProblemOrClaimModel): ProblemDto {
        val data = meter.run {
            ProblemOrClaimData(
                text = this.text
            )
        }

        return meter.run {
            ProblemDto(
                managementCompanyId = managementCompanyId,
                subscriberId = subscriberId,
                data = data
            )
        }
    }

    fun mapClaimToRemote(meter: AppealProblemOrClaimModel): ClaimDto {
        val data = meter.run {
            ProblemOrClaimData(
                text = this.text
            )
        }

        return meter.run {
            ClaimDto(
                managementCompanyId = managementCompanyId,
                subscriberId = subscriberId,
                data = data
            )
        }
    }

    fun mapListToDomain(appeals: List<AppealGetDto>): List<AppealGetModel> {
        return appeals.map {
            AppealGetModel(
                id = it.id,
                managementCompanyId = it.managementCompanyId,
                subscriberId = it.subscriberId,
                typeOfAppeal = it.typeOfAppeal,
                status = it.status,
                managementCompanyName = it.name,
                description = it.data,
                attachment = it.attachment,
                createdAt = it.formatCreatedAt()
            )
        }
    }
}