package com.example.myhome.presentation.features.event

import com.example.myhome.features.event.models.EventListModel
import com.example.myhome.features.event.models.EventType
import com.example.myhome.features.event.models.HouseNotificationListItemModel
import com.example.myhome.features.event.models.OptionListItemModel
import com.example.myhome.features.event.models.VotingListItemModel
import com.example.myhome.presentation.utils.pickers.ProportionPicker

interface EventUiConverter: ProportionPicker {
    fun notificationListToUi(notification: HouseNotificationListItemModel): HouseNotificationUiModel {
        return HouseNotificationUiModel(
            id = notification.id,
            title = notification.title,
            text = notification.text,
            managementCompanyName = notification.managementCompanyName,
            createdAt = notification.createdAt
            )
    }

    fun optionListToUi(options: List<OptionListItemModel>, resultId: Int?): List<OptionUiModel> {
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

    fun votingListToUi(voting: VotingListItemModel): VotingUiModel {
        return VotingUiModel(
            id = voting.id,
            options = optionListToUi(voting.options, voting.resultId),
            managementCompanyName = voting.managementCompanyName,
            title = voting.title,
            createdAt = voting.createdAt,
            expiredAt = voting.expiredAt,
            status = voting.status
        )
    }

    fun notificationListToEventList(notifications: List<HouseNotificationListItemModel>): List<EventUiModel> {
        return notifications.map { notification ->
            EventUiModel(
                voting = null,
                notification = notificationListToUi(notification),
                createdAt = notification.createdAt,
                eventType = EventType.Notification
            )
        }
    }

    fun votingListToEventList(votings: List<VotingListItemModel>): List<EventUiModel> {
        return votings.map { voting ->
            EventUiModel(
                voting = votingListToUi(voting),
                notification = null,
                createdAt = voting.createdAt,
                eventType = EventType.Voting
            )
        }
    }

    fun eventListToUi(events: EventListModel): List<EventUiModel> {
        val notificationList = notificationListToEventList(events.notifications.notifications)
        val votingList = votingListToEventList(events.votings.votings)
        return (notificationList + votingList).sortedByDescending { it.createdAt }
    }

}