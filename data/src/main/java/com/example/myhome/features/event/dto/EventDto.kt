package com.example.myhome.features.event.dto

import com.example.myhome.models.UserRole
import com.example.myhome.features.appeal.AppealListItemResponse
import com.example.myhome.features.event.models.EventTypeResponse
import com.example.myhome.features.event.models.EventTypeRequest
import com.example.myhome.models.DateTimeConverter
import com.example.myhome.models.MetaRequest
import java.util.Date

data class EventListRequest (
    val userId: Int,
    val userRole: UserRole = UserRole.Owner,
    val eventType: EventTypeRequest,
    val meta: MetaRequest?
)

data class EventListResponse (
    val appeals: List<AppealListItemResponse>,
    val notificationsAndVotings: List<NotificationAndVotingListResponse>
)

data class NotificationAndVotingListResponse(
    val voting: VotingListItemResponse?,
    val notification: HouseNotificationListItemResponse?,
    val createdAt: String,
    val eventType: EventTypeResponse
): DateTimeConverter {
    fun formatCreatedAt(): Date {
        return parseDateTime(createdAt)
    }
}