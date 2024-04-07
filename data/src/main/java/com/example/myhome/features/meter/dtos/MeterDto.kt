package com.example.myhome.features.meter.dtos

import com.example.myhome.features.meter.models.MeterStatus
import com.example.myhome.features.meter.models.MeterType
import com.example.myhome.models.DateConverter
import com.example.myhome.models.UserRole
import java.util.Date

data class MeterListRequestExtended(
    val meterType: MeterType = MeterType.Individual,
    val isNotAllInfo: Boolean = true
)

data class MeterExtendedListResponse(
    val meters: List<MeterExtendedListItemResponse>
)

data class MeterExtendedListItemResponse(
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

data class ApartmentWithMeterListResponse(
    val meters: List<ApartmentWithMeterListItemResponse>
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
    val currentReading: Double,
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
    val meterType: MeterType = MeterType.Individual,
    val isNotAllInfo: Boolean = false
)

