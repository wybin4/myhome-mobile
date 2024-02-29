package com.example.myhome.utils.models

import com.example.myhome.appeal.models.AppealStatus
import com.example.myhome.appeal.models.AppealType
import com.example.myhome.common.models.Adaptive
import com.example.myhome.common.models.DateConverter
import com.example.myhome.utils.converters.AppealUiConverter
import com.example.myhome.utils.pickers.IconPicker
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
) : Adaptive, IconPicker, DateConverter, AppealUiConverter {
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
