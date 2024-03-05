package com.example.myhome.event.dto

import com.example.myhome.appeal.dtos.AppealGetDto
import com.example.myhome.base.models.UserRole
import com.example.myhome.common.models.Countable
import com.example.myhome.event.models.EventType

data class EventListDto (
    val userId: Int,
    val userRole: UserRole = UserRole.Owner,
    val events: List<EventType>
)

data class EventGetDto (
    val appeals: EventAppealGetDto,
    val notifications: EventNotificationGetDto,
    val votings: EventVotingGetDto
)

data class EventAppealGetDto(
    val appeals: List<AppealGetDto>,
    override val totalCount: Int
): Countable

data class EventNotificationGetDto(
    val notifications: List<HouseNotificationGetDto>,
    override val totalCount: Int
): Countable

data class EventVotingGetDto(
    val votings: List<VotingGetDto>,
    override val totalCount: Int
): Countable