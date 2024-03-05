package com.example.myhome.event.dto

import com.example.myhome.common.models.DateTimeConverter
import com.example.myhome.event.models.HouseNotificationType
import java.util.Date

data class HouseNotificationGetDto (
    val id: Int,
    val houseId: Int,
    val title: String,
    val type: HouseNotificationType,
    val createdAt: String,
    val text: String,
    val name: String
): DateTimeConverter {
    fun formatCreatedAt(): Date {
        return parseDateTime(createdAt)
    }
}