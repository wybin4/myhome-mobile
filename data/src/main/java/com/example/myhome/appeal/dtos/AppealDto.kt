package com.example.myhome.appeal.dtos

import com.example.myhome.appeal.models.AppealStatus
import com.example.myhome.appeal.models.AppealType

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
)

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