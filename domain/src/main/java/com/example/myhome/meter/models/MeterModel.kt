package com.example.myhome.meter.models

import java.util.Date

abstract class MeterModel(
    open val id: Int?,
    open val factoryNumber: String,
    open val verifiedAt: Date,
    open val issuedAt: Date,
    open val apartmentId: Int,
    open val typeOfServiceId: Int,
)

data class MeterUpdateModel(
    val id: Int,
    val verifiedAt: Date,
    val issuedAt: Date,
    val managementCompanyId: Int,
    val subscriberId: Int
)
data class MeterAddModel(
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

data class MeterGetModel(
    override val id: Int?,
    override val factoryNumber: String,
    override val verifiedAt: Date,
    override val issuedAt: Date,
    override val apartmentId: Int,
    override val typeOfServiceId: Int,
    val currentReading: Int?,
    val typeOfServiceName: String,
    val unitName: String
) : MeterModel(
    id = id,
    factoryNumber = factoryNumber,
    verifiedAt = verifiedAt,
    issuedAt = issuedAt,
    apartmentId = apartmentId,
    typeOfServiceId = typeOfServiceId
)