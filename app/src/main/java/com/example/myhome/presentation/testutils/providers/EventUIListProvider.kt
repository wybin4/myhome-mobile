package com.example.myhome.presentation.testutils.providers

import com.example.myhome.features.event.dto.EventListResponse
import com.example.myhome.features.event.dto.HouseNotificationListItemResponse
import com.example.myhome.features.event.dto.OptionListItemResponse
import com.example.myhome.features.event.dto.VoteListItemResponse
import com.example.myhome.features.event.dto.VotingListItemResponse
import com.example.myhome.features.event.models.HouseNotificationType
import com.example.myhome.features.event.models.VotingStatus
import com.example.myhome.testutils.DateDomainProvider.getDateString
import com.example.myhome.testutils.DateDomainProvider.subtractDaysString

object EventUITestListProvider {
    private val notificationList = listOf(
        HouseNotificationListItemResponse(
            id = 1,
            title = "Отключение холодной воды",
            createdAt = subtractDaysString(1),
            text = "???",
            name = "ТСЖ Прогресс",
            type = HouseNotificationType.Accident,
            houseId = 1
        ),
        HouseNotificationListItemResponse(
            id = 2,
            title = "Отключение горячей воды",
            createdAt = subtractDaysString(4),
            text = "534543534",
            name = "ТСЖ Прогресс",
            type = HouseNotificationType.Accident,
            houseId = 1
        ),
        HouseNotificationListItemResponse(
            id = 1,
            title = "Отключение холодной воды",
            createdAt = subtractDaysString(5),
            text = "213123123",
            name = "ТСЖ Прогресс",
            type = HouseNotificationType.Accident,
            houseId = 1
        )
    )

    private val voteList = mutableListOf(
        VoteListItemResponse(
            id = 2,
            userId = 2,
            optionId = 1
        ),
        VoteListItemResponse(
            id = 3,
            userId = 3,
            optionId = 1
        )
    )

    private val optionList = listOf(
        OptionListItemResponse(
            id = 1,
            text = "За",
            numberOfVotes = 2,
            votes = voteList,
            votingId = 1
        ),
        OptionListItemResponse(
            id = 2,
            text = "Против",
            numberOfVotes = 1,
            votes = listOf(
                VoteListItemResponse(
                    id = 4,
                    userId = 4,
                    optionId = 2
                )
            ),
            votingId = 1
        ),
        OptionListItemResponse(
            id = 3,
            text = "Воздержусь",
            numberOfVotes = 1,
            votes = listOf(
                VoteListItemResponse(
                    id = 5,
                    userId = 5,
                    optionId = 3
                )
            ),
            votingId = 1
        )
    )

    private fun getOptionSelected(): List<OptionListItemResponse> {
        val modifiedOptions = optionList.toMutableList()
        modifiedOptions[0] = modifiedOptions[0].copy(
            votes = modifiedOptions[0].votes.toMutableList().apply {
                add(VoteListItemResponse(optionId = 1, userId = 1, id = 1))
            }
        )
        return modifiedOptions
    }


    private val votingList = listOf(
        VotingListItemResponse(
            id = 4,
            name = "ТСЖ Прогресс",
            title = "Установка домофона",
            createdAt = subtractDaysString(7),
            expiredAt = getDateString(),
            status = VotingStatus.Close,
            houseId = 1,
            resultId = 1,
            options = getOptionSelected()
        ),
        VotingListItemResponse(
            id = 3,
            name = "ТСЖ Прогресс",
            title = "Установка домофона",
            createdAt = subtractDaysString(6),
            expiredAt = getDateString(),
            status = VotingStatus.Close,
            houseId = 1,
            resultId = 1,
            options = optionList
        ),
        VotingListItemResponse(
            id = 2,
            name = "ТСЖ Прогресс",
            title = "Установка домофона",
            createdAt = subtractDaysString(3),
            expiredAt = getDateString(),
            status = VotingStatus.Open,
            houseId = 1,
            resultId = null,
            options = getOptionSelected()
        ),
        VotingListItemResponse(
            id = 1,
            name = "ТСЖ Прогресс",
            title = "Установка домофона с видеонаблюдением",
            createdAt = subtractDaysString(2),
            expiredAt = getDateString(),
            status = VotingStatus.Open,
            houseId = 1,
            resultId = null,
            options = optionList
        )
    )

    val eventList = EventListResponse(
        notifications = notificationList,
        votings = votingList,
        appeals =  emptyList()
    )

}
