package com.example.myhome.features.meter.models

import com.example.myhome.models.DateConverter
import java.util.Date

abstract class MeterModel(
    open val id: Int?,
    open val factoryNumber: String,
    open val verifiedAt: Date,
    open val issuedAt: Date,
    open val typeOfServiceId: Int,
)

data class ApartmentWithMeterListItemModel(
    val id: Int,
    val address: String,
    val meters: List<MeterListItemModel>
)

data class MeterListItemModel(
    override val id: Int,
    override val factoryNumber: String,
    override val verifiedAt: Date,
    override val issuedAt: Date,
    override val typeOfServiceId: Int,
    val typeOfServiceName: String,
    val typeOfServiceEngName: String,
    val currentReading: Double?,
    val unitName: String
): MeterModel (
    id = id,
    factoryNumber = factoryNumber,
    verifiedAt = verifiedAt,
    issuedAt = issuedAt,
    typeOfServiceId = typeOfServiceId
), DateConverter {
    fun formatIssuedAt(): String {
        return formatDate(issuedAt)
    }
    fun formatVerifiedAt(): String {
        return formatDate(verifiedAt)
    }
}

data class MeterListItemExtendedModel(
    val id: Int,
    val typeOfServiceName: String,
    val subscriberId: Int,
    val address: String
)
