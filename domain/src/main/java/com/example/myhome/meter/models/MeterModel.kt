package com.example.myhome.meter.models

import com.example.myhome.common.models.Adaptive
import java.util.Date

abstract class MeterModel(
    open val id: Int?,
    open val factoryNumber: String,
    open val verifiedAt: Date,
    open val issuedAt: Date,
    open val typeOfServiceId: Int,
)

data class ApartmentWithMeterGetModel(
    val id: Int,
    val address: String,
    val meters: List<MeterGetModel>
)

data class MeterGetModel(
    override val id: Int,
    override val factoryNumber: String,
    override val verifiedAt: Date,
    override val issuedAt: Date,
    override val typeOfServiceId: Int,
    val typeOfServiceName: String,
    val currentReading: Double?,
    val unitName: String
): MeterModel (
    id = id,
    factoryNumber = factoryNumber,
    verifiedAt = verifiedAt,
    issuedAt = issuedAt,
    typeOfServiceId = typeOfServiceId
)
