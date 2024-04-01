package com.example.myhome.presentation.features.event.converters

import com.example.myhome.features.event.dto.HouseNotificationListItemResponse
import com.example.myhome.features.event.dto.NotificationAndVotingListItemResponse
import com.example.myhome.features.event.dto.OptionListItemResponse
import com.example.myhome.features.event.dto.VotingListItemResponse
import com.example.myhome.presentation.features.event.EventUiModel
import com.example.myhome.presentation.features.event.HouseNotificationUiModel
import com.example.myhome.presentation.features.event.OptionUiModel
import com.example.myhome.presentation.features.event.VotingUiModel
import com.example.myhome.presentation.utils.pickers.ProportionPicker
import java.util.Date

interface EventUiConverter: ProportionPicker {
    private fun notificationListToUi(notification: HouseNotificationListItemResponse, createdAt: Date): HouseNotificationUiModel {
        return HouseNotificationUiModel(
            id = notification.id,
            title = notification.title,
            text = notification.text,
            managementCompanyName = notification.name,
            createdAt = createdAt
            )
    }

    private fun optionListToUi(options: List<OptionListItemResponse>, resultId: Int?): List<OptionUiModel> {
        val totalVotes = options.sumOf { it.numberOfVotes }

        return options.map { option ->
            val selected = option.votes.any { vote -> vote.userId == 1 }
            OptionUiModel(
                id = option.id,
                text = option.text,
                numberOfVotes = option.numberOfVotes,
                proportion = calculateProportion(option.numberOfVotes, totalVotes),
                selected = selected,
                isResult = option.id == resultId
            )
        }
    }

    private fun votingListToUi(voting: VotingListItemResponse, createdAt: Date): VotingUiModel {
        return VotingUiModel(
            id = voting.id,
            options = optionListToUi(voting.options, voting.resultId),
            managementCompanyName = voting.name,
            title = voting.title,
            createdAt = createdAt,
            expiredAt = voting.formatExpiredAt(),
            status = voting.status
        )
    }

    fun eventToUi(notificationAndVoting: NotificationAndVotingListItemResponse): EventUiModel {
        return notificationAndVoting.run {
            val date = formatCreatedAt()
            EventUiModel(
                voting = voting?.let { votingListToUi(it, date) },
                notification = notification?.let { notificationListToUi(it, date) },
                createdAt = date,
                eventType = eventType
            )
        }
    }
}