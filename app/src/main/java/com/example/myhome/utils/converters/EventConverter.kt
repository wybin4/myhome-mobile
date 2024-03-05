package com.example.myhome.utils.converters

import com.example.myhome.event.models.EventGetModel
import com.example.myhome.event.models.EventType
import com.example.myhome.event.models.HouseNotificationGetModel
import com.example.myhome.event.models.VotingGetModel
import com.example.myhome.utils.models.EventUiModel
import com.example.myhome.utils.models.HouseNotificationUiModel
import com.example.myhome.utils.models.VotingUiModel

interface EventUiConverter {
    fun notificationListToUi(notification: HouseNotificationGetModel): HouseNotificationUiModel {
        return HouseNotificationUiModel(
            id = notification.id,
            title = notification.title,
            text = notification.text,
            managementCompanyName = notification.managementCompanyName,
            createdAt = notification.createdAt
            )
    }

    fun votingListToUi(voting: VotingGetModel): VotingUiModel {
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

    fun notificationListToEventList(notifications: List<HouseNotificationGetModel>): List<EventUiModel> {
        return notifications.map { notification ->
            EventUiModel(
                voting = null,
                notification = notificationListToUi(notification),
                createdAt = notification.createdAt,
                eventType = EventType.Notification
            )
        }
    }

    fun votingListToEventList(votings: List<VotingGetModel>): List<EventUiModel> {
        return votings.map { voting ->
            EventUiModel(
                voting = votingListToUi(voting),
                notification = null,
                createdAt = voting.createdAt,
                eventType = EventType.Voting
            )
        }
    }

    fun eventListToUi(events: EventGetModel): List<EventUiModel> {
        val notificationList = notificationListToEventList(events.notifications.notifications)
        val votingList = votingListToEventList(events.votings.votings)
        return (notificationList + votingList).sortedByDescending { it.createdAt }
    }

}