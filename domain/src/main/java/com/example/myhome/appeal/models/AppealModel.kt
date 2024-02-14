package com.example.myhome.appeal.models

import com.example.myhome.meter.models.MeterModel
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