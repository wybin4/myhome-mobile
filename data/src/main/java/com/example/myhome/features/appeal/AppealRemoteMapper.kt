package com.example.myhome.features.appeal

import com.example.myhome.features.appeal.models.AppealAddMeterModel
import com.example.myhome.features.appeal.models.AppealListItemModel
import com.example.myhome.features.appeal.models.AppealProblemOrClaimModel
import com.example.myhome.features.appeal.models.AppealUpdateMeterModel

class AppealRemoteMapper {
    fun mapAddToRemote(meter: AppealAddMeterModel): AppealAddMeterAddRequest {
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
            AppealAddMeterAddRequest(
                managementCompanyId = managementCompanyId,
                subscriberId = subscriberId,
                data = data
            )
        }
    }

    fun mapUpdateToRemote(meter: AppealUpdateMeterModel): AppealVerifyMeterAddRequest {
        val data = meter.run {
            VerifyIndividualMeterData(
                meterId = meterId,
                issuedAt = issuedAt.toString(),
                verifiedAt = verifiedAt.toString(),
                attachment = attachment
            )
        }

        return meter.run {
            AppealVerifyMeterAddRequest(
                managementCompanyId = managementCompanyId,
                subscriberId = subscriberId,
                data = data
            )
        }
    }

    fun mapProblemToRemote(meter: AppealProblemOrClaimModel): AppealProblemAddRequest {
        val data = meter.run {
            ProblemOrClaimData(
                text = this.text
            )
        }

        return meter.run {
            AppealProblemAddRequest(
                managementCompanyId = managementCompanyId,
                subscriberId = subscriberId,
                data = data
            )
        }
    }

    fun mapClaimToRemote(meter: AppealProblemOrClaimModel): AppealClaimAddRequest {
        val data = meter.run {
            ProblemOrClaimData(
                text = this.text
            )
        }

        return meter.run {
            AppealClaimAddRequest(
                managementCompanyId = managementCompanyId,
                subscriberId = subscriberId,
                data = data
            )
        }
    }

    fun mapListToDomain(appeals: List<AppealAddResponse>): List<AppealListItemModel> {
        return appeals.map {
            AppealListItemModel(
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