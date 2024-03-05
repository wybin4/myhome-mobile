package com.example.myhome.presentation.features.event

import com.example.myhome.features.event.models.EventType
import com.example.myhome.features.event.models.OptionListItemModel
import com.example.myhome.features.event.models.VotingStatus
import com.example.myhome.models.DateConverter
import com.example.myhome.models.DateTimeConverter
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
    val options: List<OptionListItemModel>,
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