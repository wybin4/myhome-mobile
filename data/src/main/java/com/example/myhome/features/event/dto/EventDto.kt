package com.example.myhome.features.event.dto

import com.example.myhome.models.UserRole
import com.example.myhome.features.appeal.AppealListItemResponse
import com.example.myhome.features.event.models.EventType

data class MetaRequest(
    val page: Int,
    val limit: Int
)

data class EventListRequest (
    val userId: Int,
    val userRole: UserRole = UserRole.Owner,
    val events: List<EventType>,
    val meta: MetaRequest?
)

data class EventListResponse (
    val appeals: List<AppealListItemResponse>,
    val notifications: List<HouseNotificationListItemResponse>,
    val votings: List<VotingListItemResponse>
)