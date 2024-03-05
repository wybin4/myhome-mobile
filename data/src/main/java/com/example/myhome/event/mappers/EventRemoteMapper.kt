package com.example.myhome.event.mappers

import com.example.myhome.event.dto.EventGetDto
import com.example.myhome.event.dto.HouseNotificationGetDto
import com.example.myhome.event.dto.VotingGetDto
import com.example.myhome.event.models.EventGetModel
import com.example.myhome.event.models.EventNotificationGetModel
import com.example.myhome.event.models.EventVotingGetModel
import com.example.myhome.event.models.HouseNotificationGetModel
import com.example.myhome.event.models.OptionGetModel
import com.example.myhome.event.models.VoteGetModel
import com.example.myhome.event.models.VotingGetModel

class EventRemoteMapper {
    private fun mapVotingListToDomain(votingList: List<VotingGetDto>): List<VotingGetModel> {
        return votingList.map {
            val options = it.options.map { option ->
                val votes = option.votes.map { vote ->
                    VoteGetModel(
                        id = vote.id,
                        userId = vote.userId
                    )
                }

                OptionGetModel(
                    id = option.id,
                    text = option.text,
                    numberOfVotes = option.numberOfVotes,
                    votes = votes
                )
            }

            VotingGetModel(
                id = it.id,
                result = it.result,
                options = options,
                managementCompanyName = it.name,
                houseId = it.houseId,
                title = it.title,
                createdAt = it.formatCreatedAt(),
                expiredAt = it.formatExpiredAt(),
                status = it.status
            )
        }
    }

    private fun mapNotificationListToDomain(notificationList: List<HouseNotificationGetDto>): List<HouseNotificationGetModel> {
        return notificationList.map {
            HouseNotificationGetModel(
                id = it.id,
                title = it.title,
                type = it.type,
                createdAt = it.formatCreatedAt(),
                text = it.text,
                managementCompanyName = it.name
            )
        }
    }

    fun mapListToDomain(eventList: EventGetDto): EventGetModel {
        val notificationList = mapNotificationListToDomain(eventList.notifications.notifications)
        val votingList = mapVotingListToDomain(eventList.votings.votings)

        return EventGetModel(
            notifications = EventNotificationGetModel(
                notifications = notificationList,
                totalCount = eventList.notifications.totalCount
            ),
            votings = EventVotingGetModel(
                votings = votingList,
                totalCount = eventList.votings.totalCount
            )
        )
    }
}