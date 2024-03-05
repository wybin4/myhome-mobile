package com.example.myhome.event.dto

import com.example.myhome.common.models.DateConverter
import com.example.myhome.common.models.DateTimeConverter
import com.example.myhome.event.models.VotingStatus
import java.util.Date

data class VotingGetDto (
    val id: Int,
    val result: String?,
    val options: List<OptionGetDto>,
    val name: String,
    val houseId: Int,
    val title: String,
    val createdAt: String,
    val expiredAt: String,
    val status: VotingStatus
): DateConverter, DateTimeConverter {
    fun formatCreatedAt(): Date {
        return parseDateTime(createdAt)
    }

    fun formatExpiredAt(): Date {
        return parseDate(expiredAt)
    }
}

data class OptionGetDto (
    val id: Int,
    val votingId: Int,
    val text: String,
    val numberOfVotes: Int,
    val votes: List<VoteGetDto>
)

data class VoteGetDto (
    val id: Int,
    val optionId: Int,
    val userId: Int
)