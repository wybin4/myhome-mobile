package com.example.myhome.features.event.dto

import com.example.myhome.features.appeal.AppealListResponse
import com.example.myhome.features.event.models.EventTypeRequest
import com.example.myhome.features.event.models.EventTypeResponse
import com.example.myhome.models.DateTimeConverter
import com.example.myhome.models.MetaRequest
import java.util.Date

data class EventListRequest (
    val eventType: EventTypeRequest,
    val meta: MetaRequest
)

data class EventListResponse (
    val events: EventListItemResponse
)

data class EventListItemResponse (
    val appeals: AppealListResponse,
    val notificationsAndVotings: NotificationAndVotingListResponse
)

data class NotificationAndVotingListResponse(
    val notificationsAndVotings: List<NotificationAndVotingListItemResponse>,
    val totalCount: Int
)

data class NotificationAndVotingListItemResponse(
    val voting: VotingListItemResponse?,
    val notification: HouseNotificationListItemResponse?,
    val createdAt: String,
    val eventType: EventTypeResponse
): DateTimeConverter {
    fun formatCreatedAt(): Date {
        return parseDateTime(createdAt)
    }
}