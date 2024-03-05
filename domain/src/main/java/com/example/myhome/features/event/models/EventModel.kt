package com.example.myhome.features.event.models

data class EventListModel(
    val notifications: EventNotificationListModel,
    val votings: EventVotingListModel
)

data class EventNotificationListModel(
    val notifications: List<HouseNotificationListItemModel>,
    val totalCount: Int
)

data class EventVotingListModel(
    val votings: List<VotingListItemModel>,
    val totalCount: Int
)