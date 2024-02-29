package com.example.myhome.event.dto

import com.example.myhome.appeal.dtos.AppealGetDto
import com.example.myhome.base.models.UserRole
import com.example.myhome.event.models.EventType

data class EventListDto (
    val userId: Int,
    val userRole: UserRole = UserRole.Owner,
    val events: List<EventType>
)

data class EventGetDto (
    val appeals: EventAppealGetDto
)

data class EventAppealGetDto(
    val appeals: List<AppealGetDto>,
    val totalCount: Int
)