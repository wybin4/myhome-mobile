package com.example.myhome.presentation.features.appeal.converters

import com.example.myhome.features.appeal.AppealAddMeterAddRequest
import com.example.myhome.features.appeal.AppealClaimAddRequest
import com.example.myhome.features.appeal.AppealProblemAddRequest
import com.example.myhome.features.appeal.AppealVerifyMeterAddRequest
import com.example.myhome.features.appeal.models.AddIndividualMeterData
import com.example.myhome.features.appeal.models.ProblemOrClaimData
import com.example.myhome.features.appeal.models.VerifyIndividualMeterData
import com.example.myhome.presentation.features.appeal.AppealAddMeterUiModel
import com.example.myhome.presentation.features.appeal.AppealProblemOrClaimUiModel
import com.example.myhome.presentation.features.appeal.AppealUpdateMeterUiModel

interface AppealRemoteConverter {
    fun addToRemote(meter: AppealAddMeterUiModel): AppealAddMeterAddRequest {
        val data = meter.run {
            AddIndividualMeterData(
                typeOfServiceId = typeOfServiceId,
                apartmentId = apartmentId,
                factoryNumber = factoryNumber,
                issuedAt = issuedAt.toString(),
                verifiedAt = verifiedAt.toString(),
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

    fun updateToRemote(meter: AppealUpdateMeterUiModel): AppealVerifyMeterAddRequest {
        val data = meter.run {
            VerifyIndividualMeterData(
                meterId = meterId,
                issuedAt = issuedAt.toString(),
                verifiedAt = verifiedAt.toString(),
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

    fun problemToRemote(meter: AppealProblemOrClaimUiModel): AppealProblemAddRequest {
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

    fun claimToRemote(meter: AppealProblemOrClaimUiModel): AppealClaimAddRequest {
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

}