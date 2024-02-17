package com.example.myhome.meter.dtos

import com.example.myhome.appeal.dtos.AddIndividualMeterData
import com.example.myhome.base.models.UserRole
import com.example.myhome.appeal.dtos.AppealAddDto
import com.example.myhome.appeal.dtos.VerifyIndividualMeterData
import com.example.myhome.appeal.models.AppealType
import com.example.myhome.common.models.DateConverter
import com.example.myhome.meter.models.MeterType
import java.util.Date

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

data class ApartmentWithMeterGetDto(
    val apartmentId: Int,
    val apartmentFullAddress: String,
    val apartmentNumber: Int,
    val meters: List<MeterGetDto>
)

data class MeterGetDto(
    val id: Int,
    val factoryNumber: String,
    val verifiedAt: String,
    val issuedAt: String,
    val typeOfServiceId: Int,
    val typeOfServiceName: String,
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

data class ApartmentWithMeterListDto(
    val userId: Int,
    val userRole: UserRole = UserRole.Owner,
    val meterType: MeterType = MeterType.Individual,
    val isNotAllInfo: Boolean = false
)

