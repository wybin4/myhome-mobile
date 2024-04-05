package com.example.myhome.presentation.features.servicenotification.models

import com.example.myhome.features.servicenotifications.models.NotificationStatus
import com.example.myhome.presentation.features.servicenotification.TimeConverter
import com.example.myhome.presentation.models.AdaptiveInt
import com.example.myhome.presentation.utils.converters.CombinedDateConverter
import com.example.myhome.presentation.utils.pickers.IconPicker
import java.util.Date

data class ServiceNotificationUiModel(
    override val id: Int,
    val managementCompanyName: String?,
    val title: String,
    val text: String,
    val type: ServiceNotificationUiType,
    val createdAt: Date,
    val status: NotificationStatus
) : AdaptiveInt, CombinedDateConverter, TimeConverter, IconPicker {
    fun isRead(): Boolean = status == NotificationStatus.Read

    private fun getDate(): String {
        return formatDate(createdAt)
    }

    private fun getTime(): String {
        return formatHHMM(createdAt)
    }

    fun formatText(): String {
        return "${getDate()} · ${getTime()}"
    }

    fun getIcon(): Int {
        return getServiceNotificationIcon(type)
    }
}