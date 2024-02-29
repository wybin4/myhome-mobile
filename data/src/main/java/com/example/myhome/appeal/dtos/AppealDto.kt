package com.example.myhome.appeal.dtos

import com.example.myhome.appeal.models.AppealStatus
import com.example.myhome.appeal.models.AppealType
import com.example.myhome.common.models.DateConverter
import java.util.Date

abstract class AppealAddDto (
    open val managementCompanyId: Int,
    open val subscriberId: Int,
    open val typeOfAppeal: AppealType,
    open val data: AppealData
)
data class AppealGetDto (
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

abstract class AppealData { }

data class AddIndividualMeterData(
    val typeOfServiceId: Int,
    val apartmentId: Int,
    val factoryNumber: String,
    val issuedAt: String,
    val verifiedAt: String,
    val attachment: String
): AppealData()

data class VerifyIndividualMeterData(
    val meterId: Int,
    val verifiedAt: String,
    val issuedAt: String,
    val attachment: String
) : AppealData()

data class ProblemOrClaimData(
    val text: String
) : AppealData()

data class ProblemDto(
    override val managementCompanyId: Int,
    override val subscriberId: Int,
    override val typeOfAppeal: AppealType = AppealType.ProblemOrQuestion,
    override val data: ProblemOrClaimData
) : AppealAddDto(
    managementCompanyId = managementCompanyId,
    subscriberId = subscriberId,
    typeOfAppeal = typeOfAppeal,
    data = data
)

data class ClaimDto(
    override val managementCompanyId: Int,
    override val subscriberId: Int,
    override val typeOfAppeal: AppealType = AppealType.Claim,
    override val data: ProblemOrClaimData
) : AppealAddDto(
    managementCompanyId = managementCompanyId,
    subscriberId = subscriberId,
    typeOfAppeal = typeOfAppeal,
    data = data
)