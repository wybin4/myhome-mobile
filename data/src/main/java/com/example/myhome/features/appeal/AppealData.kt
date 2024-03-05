package com.example.myhome.features.appeal

abstract class AppealData

data class AddIndividualMeterData(
    val typeOfServiceId: Int,
    val apartmentId: Int,
    val factoryNumber: String,
    val issuedAt: String,
    val verifiedAt: String,
    val attachment: String
): AppealData()

data class VerifyIndividualMeterData(
    val meterId: Int,
    val verifiedAt: String,
    val issuedAt: String,
    val attachment: String
) : AppealData()

data class ProblemOrClaimData(
    val text: String
) : AppealData()