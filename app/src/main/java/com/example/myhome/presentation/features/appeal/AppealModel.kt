package com.example.myhome.presentation.features.appeal

import com.example.myhome.features.appeal.models.AppealStatus
import com.example.myhome.features.appeal.models.AppealType
import com.example.myhome.models.DateConverter
import com.example.myhome.presentation.models.Adaptive
import com.example.myhome.presentation.utils.pickers.IconPicker
import java.util.Date

data class AppealUiModel(
    override val id: Int,
    val managementCompanyId: Int,
    val subscriberId: Int,
    val typeOfAppeal: AppealType,
    val status: AppealStatus,
    val managementCompanyName: String,
    val description: String,
    val attachment: String?,
    val createdAt: Date
) : Adaptive, IconPicker, DateConverter, AppealUiPicker {
    fun getTypeIcon(): Int? {
        return getAppealTypeIcon(typeOfAppeal)
    }

    fun getStatusIcon(): Int? {
        return getAppealStatusIcon(status)
    }

    fun getStatusString(): String {
        return getStatus(status)
    }

    fun getTypeString(): String {
        return getType(typeOfAppeal)
    }

    fun getTitleWithType(): String {
        val type = getTypeString()
        return "$type №$id"
    }

    fun getTitle(): String {
        return "Обращение №$id"
    }

    fun getSubtitle(): String {
        val formattedDate = formatDate(createdAt)
        return "$managementCompanyName · $formattedDate"
    }

    fun formatCreatedAt(): String {
        return formatDate(createdAt)
    }

    fun hasAttachment(): Boolean {
        return attachment != null
    }

}

data class AppealAddMeterUiModel(
    val id: Int?,
    val factoryNumber: String,
    val verifiedAt: Date,
    val issuedAt: Date,
    val apartmentId: Int,
    val typeOfServiceId: Int,
    val managementCompanyId: Int,
    val subscriberId: Int
)

data class AppealUpdateMeterUiModel(
    val meterId: Int,
    val verifiedAt: Date,
    val issuedAt: Date,
    val managementCompanyId: Int,
    val subscriberId: Int
)

data class AppealProblemOrClaimUiModel(
    val managementCompanyId: Int,
    val subscriberId: Int,
    val text: String,
)