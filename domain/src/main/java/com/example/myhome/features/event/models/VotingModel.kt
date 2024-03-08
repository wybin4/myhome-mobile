package com.example.myhome.features.event.models

import java.util.Date

data class VotingListItemModel (
    val id: Int,
    val resultId: Int?,
    val options: List<OptionListItemModel>,
    val managementCompanyName: String,
    val houseId: Int,
    val title: String,
    val createdAt: Date,
    val expiredAt: Date,
    val status: VotingStatus
)

data class OptionListItemModel (
    val id: Int,
    val text: String,
    val numberOfVotes: Int,
    var votes: List<VoteListItemModel>
)

data class VoteListItemModel (
    val id: Int,
    val userId: Int
)

data class VotingUpdateModel (
    val optionId: Int,
    val userId: Int,
)