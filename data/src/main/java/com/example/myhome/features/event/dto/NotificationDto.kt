package com.example.myhome.features.event.dto

import com.example.myhome.features.event.models.HouseNotificationType
import com.example.myhome.models.DateTimeConverter
import java.util.Date

data class HouseNotificationListItemResponse (
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