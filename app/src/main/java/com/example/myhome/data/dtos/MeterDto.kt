package com.example.myhome.data.dtos

import com.example.myhome.data.models.MeterType
import com.example.myhome.data.models.UserRole

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

