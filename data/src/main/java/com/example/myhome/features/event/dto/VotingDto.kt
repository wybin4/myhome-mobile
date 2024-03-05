package com.example.myhome.features.event.dto

import com.example.myhome.features.event.models.VotingStatus
import com.example.myhome.models.DateConverter
import com.example.myhome.models.DateTimeConverter
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