package com.example.myhome.features.meter.dtos

import com.example.myhome.models.UserRole
import com.example.myhome.features.meter.models.MeterStatus
import com.example.myhome.features.meter.models.MeterType
import com.example.myhome.models.DateConverter
import java.util.Date

data class MeterListRequestExtended(
    val userId: Int,
    val userRole: UserRole = UserRole.Owner,
    val meterType: MeterType = MeterType.Individual,
    val isNotAllInfo: Boolean = true
)

data class MeterListItemResponseExtended(
    val id: Int,
    val factoryNumber: String,
    val verifiedAt: String,
    val issuedAt: String,
    val typeOfServiceId: Int,
    val typeOfServiceName: String,
    val typeOfServiceEngName: String,
    val apartmentId: Int,
    val subscriberId: Int,
    val address: String,
    val status: MeterStatus
)

data class ApartmentWithMeterListItemResponse(
    val apartmentId: Int,
    val apartmentFullAddress: String,
    val apartmentNumber: Int,
    val meters: List<MeterListItemResponse>
)

data class MeterListItemResponse(
    val id: Int,
    val factoryNumber: String,
    val verifiedAt: String,
    val issuedAt: String,
    val typeOfServiceId: Int,
    val typeOfServiceName: String,
    val typeOfServiceEngName: String,
    val currentReading: Double?,
    val unitName: String
): DateConverter {
    fun formatIssuedAt(): Date {
        return parseDate(issuedAt)
    }
    fun formatVerifiedAt(): Date {
        return parseDate(verifiedAt)
    }
}

data class ApartmentWithMeterListRequest(
    val userId: Int,
    val userRole: UserRole = UserRole.Owner,
    val meterType: MeterType = MeterType.Individual,
    val isNotAllInfo: Boolean = false
)

