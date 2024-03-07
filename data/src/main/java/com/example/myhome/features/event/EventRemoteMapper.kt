package com.example.myhome.features.event

import com.example.myhome.features.event.dto.EventListResponse
import com.example.myhome.features.event.dto.HouseNotificationListItemResponse
import com.example.myhome.features.event.dto.VotingListItemResponse
import com.example.myhome.features.event.dto.VotingUpdateRequest
import com.example.myhome.features.event.models.EventListModel
import com.example.myhome.features.event.models.EventNotificationListModel
import com.example.myhome.features.event.models.EventVotingListModel
import com.example.myhome.features.event.models.HouseNotificationListItemModel
import com.example.myhome.features.event.models.OptionListItemModel
import com.example.myhome.features.event.models.VoteListItemModel
import com.example.myhome.features.event.models.VotingListItemModel
import com.example.myhome.features.event.models.VotingUpdateModel

class EventRemoteMapper {
    private fun mapVotingListToDomain(votingList: List<VotingListItemResponse>): List<VotingListItemModel> {
        return votingList.map {
            val options = it.options.map { option ->
                val votes = option.votes.map { vote ->
                    VoteListItemModel(
                        id = vote.id,
                        userId = vote.userId
                    )
                }

                OptionListItemModel(
                    id = option.id,
                    text = option.text,
                    numberOfVotes = option.numberOfVotes,
                    votes = votes
                )
            }

            VotingListItemModel(
                id = it.id,
                resultId = it.resultId,
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

    private fun mapNotificationListToDomain(notificationList: List<HouseNotificationListItemResponse>): List<HouseNotificationListItemModel> {
        return notificationList.map {
            HouseNotificationListItemModel(
                id = it.id,
                title = it.title,
                type = it.type,
                createdAt = it.formatCreatedAt(),
                text = it.text,
                managementCompanyName = it.name
            )
        }
    }

    fun mapListToDomain(eventList: EventListResponse): EventListModel {
        val notificationList = mapNotificationListToDomain(eventList.notifications.notifications)
        val votingList = mapVotingListToDomain(eventList.votings.votings)

        return EventListModel(
            notifications = EventNotificationListModel(
                notifications = notificationList,
                totalCount = eventList.notifications.totalCount
            ),
            votings = EventVotingListModel(
                votings = votingList,
                totalCount = eventList.votings.totalCount
            )
        )
    }

    fun updateToRemote(vote: VotingUpdateModel): VotingUpdateRequest {
        return VotingUpdateRequest(
            optionId = vote.optionId,
            userId = vote.userId
        )
    }
}