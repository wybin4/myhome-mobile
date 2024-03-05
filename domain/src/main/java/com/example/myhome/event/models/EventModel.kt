package com.example.myhome.event.models

import com.example.myhome.common.models.Countable

data class EventGetModel(
    val notifications: EventNotificationGetModel,
    val votings: EventVotingGetModel
)

data class EventNotificationGetModel(
    val notifications: List<HouseNotificationGetModel>,
    override val totalCount: Int
): Countable

data class EventVotingGetModel(
    val votings: List<VotingGetModel>,
    override val totalCount: Int
): Countable