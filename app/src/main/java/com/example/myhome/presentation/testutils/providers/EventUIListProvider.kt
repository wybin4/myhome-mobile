package com.example.myhome.presentation.testutils.providers

import com.example.myhome.features.appeal.AppealListResponse
import com.example.myhome.features.event.dto.EventListItemResponse
import com.example.myhome.features.event.dto.EventListResponse
import com.example.myhome.features.event.dto.HouseNotificationListItemResponse
import com.example.myhome.features.event.dto.NotificationAndVotingListItemResponse
import com.example.myhome.features.event.dto.NotificationAndVotingListResponse
import com.example.myhome.features.event.dto.OptionListItemResponse
import com.example.myhome.features.event.dto.VoteListItemResponse
import com.example.myhome.features.event.dto.VotingListItemResponse
import com.example.myhome.features.event.models.EventTypeResponse
import com.example.myhome.features.event.models.HouseNotificationType
import com.example.myhome.features.event.models.VotingStatus
import com.example.myhome.testutils.DateDomainProvider.getDateString
import com.example.myhome.testutils.DateDomainProvider.subtractDaysString

object EventUITestListProvider {
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

    private val notificationList = listOf(
        NotificationAndVotingListItemResponse(
            notification = HouseNotificationListItemResponse(
                id = 1,
                title = "Отключение холодной воды",
                text = "???",
                name = "ТСЖ Прогресс",
                type = HouseNotificationType.Accident,
                houseId = 1
            ),
            voting = null,
            createdAt = subtractDaysString(1),
            eventType = EventTypeResponse.Notification
        ),
        NotificationAndVotingListItemResponse(
            notification = null,
            voting = VotingListItemResponse(
                id = 1,
                name = "ТСЖ Прогресс",
                title = "Установка домофона с видеонаблюдением",
                expiredAt = getDateString(),
                status = VotingStatus.Open,
                houseId = 1,
                resultId = null,
                options = optionList
            ),
            createdAt = subtractDaysString(2),
            eventType = EventTypeResponse.Notification
        ),
        NotificationAndVotingListItemResponse(
            notification = null,
            voting = VotingListItemResponse(
                id = 2,
                name = "ТСЖ Прогресс",
                title = "Установка домофона",
                expiredAt = getDateString(),
                status = VotingStatus.Open,
                houseId = 1,
                resultId = null,
                options = getOptionSelected()
            ),
            createdAt = subtractDaysString(3),
            eventType = EventTypeResponse.Notification
        ),
        NotificationAndVotingListItemResponse(
            notification = HouseNotificationListItemResponse(
                id = 2,
                title = "Отключение горячей воды",
                text = "534543534",
                name = "ТСЖ Прогресс",
                type = HouseNotificationType.Accident,
                houseId = 1
            ),
            voting = null,
            createdAt = subtractDaysString(4),
            eventType = EventTypeResponse.Notification
        ),
        NotificationAndVotingListItemResponse(
            notification = HouseNotificationListItemResponse(
                id = 1,
                title = "Отключение холодной воды",
                text = "213123123",
                name = "ТСЖ Прогресс",
                type = HouseNotificationType.Accident,
                houseId = 1
            ),
            voting = null,
            createdAt = subtractDaysString(5),
            eventType = EventTypeResponse.Notification
        ),
        NotificationAndVotingListItemResponse(
            notification = null,
            voting = VotingListItemResponse(
                id = 3,
                name = "ТСЖ Прогресс",
                title = "Установка домофона",
                expiredAt = getDateString(),
                status = VotingStatus.Close,
                houseId = 1,
                resultId = 1,
                options = optionList
            ),
            createdAt = subtractDaysString(6),
            eventType = EventTypeResponse.Notification
        ),
        NotificationAndVotingListItemResponse(
            notification = null,
            voting = VotingListItemResponse(
                id = 4,
                name = "ТСЖ Прогресс",
                title = "Установка домофона",
                expiredAt = getDateString(),
                status = VotingStatus.Close,
                houseId = 1,
                resultId = 1,
                options = getOptionSelected()
            ),
            createdAt = subtractDaysString(7),
            eventType = EventTypeResponse.Notification
        )
    )

    val eventList = EventListResponse(
        events = EventListItemResponse(
            notificationsAndVotings = NotificationAndVotingListResponse(
                notificationsAndVotings = notificationList,
                totalCount = -1
            ),
            appeals = AppealListResponse(
                appeals = emptyList(),
                totalCount = -1
            )
        )
    )

}
