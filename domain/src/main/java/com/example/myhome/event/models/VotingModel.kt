package com.example.myhome.event.models

import java.util.Date

data class VotingGetModel (
    val id: Int,
    val result: String?,
    val options: List<OptionGetModel>,
    val managementCompanyName: String,
    val houseId: Int,
    val title: String,
    val createdAt: Date,
    val expiredAt: Date,
    val status: VotingStatus
)

data class OptionGetModel (
    val id: Int,
    val text: String,
    val numberOfVotes: Int,
    val votes: List<VoteGetModel>
)

data class VoteGetModel (
    val id: Int,
    val userId: Int
)