package com.example.myhome.presentation.features.event

import com.example.myhome.features.event.models.EventListModel
import com.example.myhome.features.event.models.EventType
import com.example.myhome.features.event.models.HouseNotificationListItemModel
import com.example.myhome.features.event.models.VotingListItemModel

interface EventUiConverter {
    fun notificationListToUi(notification: HouseNotificationListItemModel): HouseNotificationUiModel {
        return HouseNotificationUiModel(
            id = notification.id,
            title = notification.title,
            text = notification.text,
            managementCompanyName = notification.managementCompanyName,
            createdAt = notification.createdAt
            )
    }

    fun votingListToUi(voting: VotingListItemModel): VotingUiModel {
        return VotingUiModel(
            id = voting.id,
            result = voting.result,
            options = voting.options,
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