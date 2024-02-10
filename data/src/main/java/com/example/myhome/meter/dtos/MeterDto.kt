package com.example.myhome.meter.dtos

import com.example.myhome.base.models.UserRole
import com.example.myhome.meter.models.MeterType

data class MeterAddDto(
    val id: Int?,
    val factoryNumber: String,
    val verifiedAt: String,
    val issuedAt: String,
    val apartmentId: Int,
    val typeOfServiceId: Int,
    val previousReading: Int?,
    val previousReadAt: String?
)

data class MeterGetDto(
    val id: Int?,
    val factoryNumber: String,
    val verifiedAt: String,
    val issuedAt: String,
    val apartmentId: Int,
    val typeOfServiceId: Int,
    val currentReading: Int?,
    val typeOfServiceName: String,
    val unitName: String
)

data class MeterUpdateDto(
    val id: Int,
    val verifiedAt: String,
    val issuedAt: String
)

data class MeterListDto(
    val userId: Int,
    val userRole: UserRole = UserRole.Owner,
    val meterType: MeterType = MeterType.Individual,
    val isNotAllInfo: Boolean = false
)

