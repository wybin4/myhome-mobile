package com.example.myhome.features.event.dto

import com.example.myhome.features.event.models.VotingStatus
import com.example.myhome.models.DateConverter
import java.util.Date

data class VotingListItemResponse (
    val id: Int,
    val resultId: Int?,
    val options: List<OptionListItemResponse>,
    val name: String,
    val houseId: Int,
    val title: String,
    val expiredAt: String,
    val status: VotingStatus
): DateConverter {
    fun formatExpiredAt(): Date {
        return parseDate(expiredAt)
    }
}

data class OptionListItemResponse (
    val id: Int,
    val votingId: Int,
    val text: String,
    val numberOfVotes: Int,
    val votes: List<VoteListItemResponse>
)

data class VoteListItemResponse (
    val id: Int,
    val optionId: Int,
    val userId: Int
)

data class VotingUpdateRequest (
    val optionId: Int,
    val userId: Int
)