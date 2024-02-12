package com.example.myhome.appeal.models

import com.example.myhome.meter.models.MeterModel
import java.util.Date

data class AppealAddMeterModel(
    override val id: Int?,
    override val factoryNumber: String,
    override val verifiedAt: Date,
    override val issuedAt: Date,
    override val apartmentId: Int,
    override val typeOfServiceId: Int,
    val managementCompanyId: Int,
    val subscriberId: Int
) : MeterModel(
    id = id,
    factoryNumber = factoryNumber,
    verifiedAt = verifiedAt,
    issuedAt = issuedAt,
    apartmentId = apartmentId,
    typeOfServiceId = typeOfServiceId
)

data class AppealUpdateMeterModel(
    val meterId: Int,
    val verifiedAt: Date,
    val issuedAt: Date,
    val managementCompanyId: Int,
    val subscriberId: Int
)