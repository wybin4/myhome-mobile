package com.example.myhome.presentation.features.event

import com.example.myhome.features.event.models.EventType
import com.example.myhome.features.event.models.VotingStatus
import com.example.myhome.models.DateConverter
import com.example.myhome.models.DateTimeConverter
import com.example.myhome.presentation.utils.converters.PercentConverter
import com.example.myhome.presentation.utils.pickers.ProportionPicker
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
    val options: List<OptionUiModel>,
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

    fun isSomeSelected(): Boolean {
        return options.any { it.selected }
    }

    fun isClosed(): Boolean {
        return status == VotingStatus.Close
    }

}

data class OptionUiModel (
    val id: Int,
    val text: String,
    var proportion: Double,
    var numberOfVotes: Int,
    var selected: Boolean = false,
    var isResult: Boolean = false
): ProportionPicker, PercentConverter {
    fun getFormattedProportion(): String {
        return formatDouble0F(proportion)
    }

    fun calculateProportion(totalVotes: Int) {
        proportion = calculateProportion(numberOfVotes, totalVotes)
    }
}