package com.example.myhome.features.appeal.models

import com.example.myhome.features.meter.models.MeterModel
import java.util.Date

data class AppealAddMeterModel(
    override val id: Int?,
    override val factoryNumber: String,
    override val verifiedAt: Date,
    override val issuedAt: Date,
    val apartmentId: Int,
    override val typeOfServiceId: Int,
    val attachment: String,
    val managementCompanyId: Int,
    val subscriberId: Int
) : MeterModel(
    id = id,
    factoryNumber = factoryNumber,
    verifiedAt = verifiedAt,
    issuedAt = issuedAt,
    typeOfServiceId = typeOfServiceId
)

data class AppealUpdateMeterModel(
    val meterId: Int,
    val verifiedAt: Date,
    val issuedAt: Date,
    val managementCompanyId: Int,
    val subscriberId: Int,
    val attachment: String
)

data class AppealProblemOrClaimModel(
    val managementCompanyId: Int,
    val subscriberId: Int,
    val text: String,
)

data class AppealListItemModel(
    val id: Int,
    val managementCompanyId: Int,
    val subscriberId: Int,
    val typeOfAppeal: AppealType,
    val status: AppealStatus,
    val managementCompanyName: String,
    val description: String,
    val attachment: String?,
    val createdAt: Date
)