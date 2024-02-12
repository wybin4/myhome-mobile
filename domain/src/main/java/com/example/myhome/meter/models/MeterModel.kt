package com.example.myhome.meter.models

import com.example.myhome.common.models.Adaptive
import java.util.Date

abstract class MeterModel(
    open val id: Int?,
    open val factoryNumber: String,
    open val verifiedAt: Date,
    open val issuedAt: Date,
    open val apartmentId: Int,
    open val typeOfServiceId: Int,
)

data class MeterGetModel(
    override val id: Int,
    override val factoryNumber: String,
    override val verifiedAt: Date,
    override val issuedAt: Date,
    override val apartmentId: Int,
    override val typeOfServiceId: Int,
    val currentReading: Double?,
    val typeOfServiceName: String,
    val unitName: String
) : MeterModel(
    id = id,
    factoryNumber = factoryNumber,
    verifiedAt = verifiedAt,
    issuedAt = issuedAt,
    apartmentId = apartmentId,
    typeOfServiceId = typeOfServiceId
), Adaptive