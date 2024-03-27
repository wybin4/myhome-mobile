package com.example.myhome.presentation.features.event.converters

import com.example.myhome.features.event.dto.EventListResponse
import com.example.myhome.features.event.dto.HouseNotificationListItemResponse
import com.example.myhome.features.event.dto.OptionListItemResponse
import com.example.myhome.features.event.dto.VotingListItemResponse
import com.example.myhome.features.event.models.EventType
import com.example.myhome.presentation.features.event.EventUiModel
import com.example.myhome.presentation.features.event.HouseNotificationUiModel
import com.example.myhome.presentation.features.event.OptionUiModel
import com.example.myhome.presentation.features.event.VotingUiModel
import com.example.myhome.presentation.utils.pickers.ProportionPicker

interface EventUiConverter: ProportionPicker {
    private fun notificationListToUi(notification: HouseNotificationListItemResponse): HouseNotificationUiModel {
        return HouseNotificationUiModel(
            id = notification.id,
            title = notification.title,
            text = notification.text,
            managementCompanyName = notification.name,
            createdAt = notification.formatCreatedAt()
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

    private fun votingListToUi(voting: VotingListItemResponse): VotingUiModel {
        return VotingUiModel(
            id = voting.id,
            options = optionListToUi(voting.options, voting.resultId),
            managementCompanyName = voting.name,
            title = voting.title,
            createdAt = voting.formatCreatedAt(),
            expiredAt = voting.formatExpiredAt(),
            status = voting.status
        )
    }

    private fun notificationListToEventList(notifications: List<HouseNotificationListItemResponse>): List<EventUiModel> {
        return notifications.map { notification ->
            EventUiModel(
                voting = null,
                notification = notificationListToUi(notification),
                createdAt = notification.formatCreatedAt(),
                eventType = EventType.Notification
            )
        }
    }

    private fun votingListToEventList(votings: List<VotingListItemResponse>): List<EventUiModel> {
        return votings.map { voting ->
            EventUiModel(
                voting = votingListToUi(voting),
                notification = null,
                createdAt = voting.formatCreatedAt(),
                eventType = EventType.Voting
            )
        }
    }

    fun eventListToUi(events: EventListResponse): List<EventUiModel> {
        val notificationList = notificationListToEventList(events.notifications)
        val votingList = votingListToEventList(events.votings)
        return (notificationList + votingList).sortedByDescending { it.createdAt }
    }

}