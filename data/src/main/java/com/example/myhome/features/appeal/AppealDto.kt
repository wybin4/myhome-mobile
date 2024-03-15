package com.example.myhome.features.appeal

import com.example.myhome.features.appeal.models.AddIndividualMeterData
import com.example.myhome.features.appeal.models.AppealData
import com.example.myhome.features.appeal.models.AppealStatus
import com.example.myhome.features.appeal.models.AppealType
import com.example.myhome.features.appeal.models.ProblemOrClaimData
import com.example.myhome.features.appeal.models.VerifyIndividualMeterData
import com.example.myhome.models.DateConverter
import java.util.Date

abstract class AppealAddRequest (
    open val managementCompanyId: Int,
    open val subscriberId: Int,
    open val typeOfAppeal: AppealType,
    open val data: AppealData
)

data class AppealProblemAddRequest(
    override val managementCompanyId: Int,
    override val subscriberId: Int,
    override val typeOfAppeal: AppealType = AppealType.ProblemOrQuestion,
    override val data: ProblemOrClaimData
) : AppealAddRequest(
    managementCompanyId = managementCompanyId,
    subscriberId = subscriberId,
    typeOfAppeal = typeOfAppeal,
    data = data
)

data class AppealAddMeterAddRequest(
    override val managementCompanyId: Int,
    override val subscriberId: Int,
    override val typeOfAppeal: AppealType = AppealType.AddIndividualMeter,
    override val data: AddIndividualMeterData
) : AppealAddRequest(
    managementCompanyId = managementCompanyId,
    subscriberId = subscriberId,
    typeOfAppeal = typeOfAppeal,
    data = data
)

data class AppealVerifyMeterAddRequest(
    override val managementCompanyId: Int,
    override val subscriberId: Int,
    override val typeOfAppeal: AppealType = AppealType.VerifyIndividualMeter,
    override val data: VerifyIndividualMeterData
) : AppealAddRequest(
    managementCompanyId = managementCompanyId,
    subscriberId = subscriberId,
    typeOfAppeal = typeOfAppeal,
    data = data
)

data class AppealClaimAddRequest(
    override val managementCompanyId: Int,
    override val subscriberId: Int,
    override val typeOfAppeal: AppealType = AppealType.Claim,
    override val data: ProblemOrClaimData
) : AppealAddRequest(
    managementCompanyId = managementCompanyId,
    subscriberId = subscriberId,
    typeOfAppeal = typeOfAppeal,
    data = data
)

data class AppealListItemResponse (
    val id: Int,
    val managementCompanyId: Int,
    val subscriberId: Int,
    val typeOfAppeal: AppealType,
    val createdAt: String,
    val status: AppealStatus,
    val name: String,
    val attachment: String?,
    val data: String
): DateConverter {
    fun formatCreatedAt(): Date {
        return parseDate(createdAt)
    }
}