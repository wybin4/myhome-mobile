package com.example.myhome.presentation.testutils.providers

import com.example.myhome.features.event.models.EventListModel
import com.example.myhome.features.event.models.EventNotificationListModel
import com.example.myhome.features.event.models.EventVotingListModel
import com.example.myhome.features.event.models.HouseNotificationListItemModel
import com.example.myhome.features.event.models.HouseNotificationType
import com.example.myhome.features.event.models.OptionListItemModel
import com.example.myhome.features.event.models.VoteListItemModel
import com.example.myhome.features.event.models.VotingListItemModel
import com.example.myhome.features.event.models.VotingStatus
import com.example.myhome.presentation.features.event.HouseNotificationUiModel
import com.example.myhome.presentation.features.event.OptionUiModel
import com.example.myhome.presentation.features.event.VotingUiModel
import com.example.myhome.testutils.providers.DateDomainProvider.date
import com.example.myhome.testutils.providers.DateDomainProvider.subtractDays

object EventUITestListProvider {
    private val notificationList = listOf(
        HouseNotificationListItemModel(
            id = 1,
            title = "Отключение холодной воды",
            createdAt = subtractDays(1),
            text = "???",
            managementCompanyName = "ТСЖ Прогресс",
            type = HouseNotificationType.Accident
        ),
        HouseNotificationListItemModel(
            id = 2,
            title = "Отключение горячей воды",
            createdAt = subtractDays(4),
            text = "534543534",
            managementCompanyName = "ТСЖ Прогресс",
            type = HouseNotificationType.Accident
        ),
        HouseNotificationListItemModel(
            id = 1,
            title = "Отключение холодной воды",
            createdAt = subtractDays(5),
            text = "213123123",
            managementCompanyName = "ТСЖ Прогресс",
            type = HouseNotificationType.Accident
        )
    )

    private val voteList = mutableListOf(
        VoteListItemModel(
            id = 2,
            userId = 2
        ),
        VoteListItemModel(
            id = 3,
            userId = 3
        )
    )

    private val optionList = listOf(
        OptionListItemModel(
            id = 1,
            text = "За",
            numberOfVotes = 2,
            votes = voteList
        ),
        OptionListItemModel(
            id = 2,
            text = "Против",
            numberOfVotes = 1,
            votes = listOf(
                VoteListItemModel(
                    id = 4,
                    userId = 4
                )
            )
        ),
        OptionListItemModel(
            id = 3,
            text = "Воздержусь",
            numberOfVotes = 1,
            votes = listOf(
                VoteListItemModel(
                    id = 5,
                    userId = 5
                )
            )
        )
    )

    private fun getOptionSelected(): List<OptionListItemModel> {
        val modifiedOptions = optionList.toMutableList()
        modifiedOptions[0] = modifiedOptions[0].copy(
            votes = modifiedOptions[0].votes.toMutableList().apply {
                add(VoteListItemModel(id = 1, userId = 1))
            }
        )
        return modifiedOptions
    }


    private val votingList = listOf(
        VotingListItemModel(
            id = 4,
            managementCompanyName = "ТСЖ Прогресс",
            title = "Установка домофона",
            createdAt = subtractDays(7),
            expiredAt = date,
            status = VotingStatus.Close,
            houseId = 1,
            resultId = 1,
            options = getOptionSelected()
        ),
        VotingListItemModel(
            id = 3,
            managementCompanyName = "ТСЖ Прогресс",
            title = "Установка домофона",
            createdAt = subtractDays(6),
            expiredAt = date,
            status = VotingStatus.Close,
            houseId = 1,
            resultId = 1,
            options = optionList
        ),
        VotingListItemModel(
            id = 2,
            managementCompanyName = "ТСЖ Прогресс",
            title = "Установка домофона",
            createdAt = subtractDays(3),
            expiredAt = date,
            status = VotingStatus.Open,
            houseId = 1,
            resultId = null,
            options = getOptionSelected()
        ),
        VotingListItemModel(
            id = 1,
            managementCompanyName = "ТСЖ Прогресс",
            title = "Установка домофона с видеонаблюдением",
            createdAt = subtractDays(2),
            expiredAt = date,
            status = VotingStatus.Open,
            houseId = 1,
            resultId = null,
            options = optionList
        )
    )

    val eventList = EventListModel(
        notifications = EventNotificationListModel(
            notifications = notificationList,
            totalCount = 3
        ),
        votings = EventVotingListModel(
            votings = votingList,
            totalCount = 4
        )
    )

}
