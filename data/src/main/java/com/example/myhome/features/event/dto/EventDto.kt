package com.example.myhome.features.event.dto

import com.example.myhome.models.UserRole
import com.example.myhome.features.appeal.AppealAddResponse
import com.example.myhome.features.event.models.EventType

data class EventListRequest (
    val userId: Int,
    val userRole: UserRole = UserRole.Owner,
    val events: List<EventType>
)

data class EventListResponse (
    val appeals: EventAppealListResponse,
    val notifications: EventNotificationListResponse,
    val votings: EventVotingListResponse
)

data class EventAppealListResponse(
    val appeals: List<AppealAddResponse>,
    val totalCount: Int
)

data class EventNotificationListResponse(
    val notifications: List<HouseNotificationGetDto>,
    val totalCount: Int
)

data class EventVotingListResponse(
    val votings: List<VotingGetDto>,
    val totalCount: Int
)