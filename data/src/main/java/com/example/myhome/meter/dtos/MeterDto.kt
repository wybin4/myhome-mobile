package com.example.myhome.meter.dtos

import com.example.myhome.appeal.dtos.AddIndividualMeterData
import com.example.myhome.base.models.UserRole
import com.example.myhome.appeal.dtos.AppealAddDto
import com.example.myhome.appeal.dtos.VerifyIndividualMeterData
import com.example.myhome.appeal.models.AppealType
import com.example.myhome.meter.models.MeterType

data class MeterAddDto(
    override val managementCompanyId: Int,
    override val subscriberId: Int,
    override val typeOfAppeal: AppealType = AppealType.AddIndividualMeter,
    override val data: AddIndividualMeterData
) : AppealAddDto(
    managementCompanyId = managementCompanyId,
    subscriberId = subscriberId,
    typeOfAppeal = typeOfAppeal,
    data = data
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
    override val managementCompanyId: Int,
    override val subscriberId: Int,
    override val typeOfAppeal: AppealType = AppealType.VerifyIndividualMeter,
    override val data: VerifyIndividualMeterData
) : AppealAddDto(
    managementCompanyId = managementCompanyId,
    subscriberId = subscriberId,
    typeOfAppeal = typeOfAppeal,
    data = data
)

data class MeterListDto(
    val userId: Int,
    val userRole: UserRole = UserRole.Owner,
    val meterType: MeterType = MeterType.Individual,
    val isNotAllInfo: Boolean = false
)

