package com.example.myhome.utils.models

import com.example.myhome.common.models.DateConverter
import com.example.myhome.common.models.DateTimeConverter
import com.example.myhome.event.models.EventType
import com.example.myhome.event.models.OptionGetModel
import com.example.myhome.event.models.VotingStatus
import java.util.Date

data class EventUiModel(
    val voting: VotingUiModel?,
    val notification: HouseNotificationUiModel?,
    val createdAt: Date,
    val eventType: EventType
)

data class HouseNotificationUiModel (
    val id: Int,
    val title: String,
    val text: String,
    val managementCompanyName: String,
    val createdAt: Date
): DateTimeConverter {
    fun formatCreatedAt(): String {
        return formatDateTime(createdAt)
    }
}

data class VotingUiModel (
    val id: Int,
    val result: String?,
    val options: List<OptionGetModel>,
    val managementCompanyName: String,
    val title: String,
    val createdAt: Date,
    val expiredAt: Date,
    val status: VotingStatus
): DateConverter, DateTimeConverter {
    fun formatCreatedAt(): String {
        return formatDateTime(createdAt)
    }

    fun formatExpiredAt(): String {
        return formatDate(expiredAt)
    }
}