package com.example.myhome.features.event.dto

import com.example.myhome.features.event.models.HouseNotificationType

data class HouseNotificationListItemResponse (
    val id: Int,
    val houseId: Int,
    val title: String,
    val type: HouseNotificationType,
    val text: String,
    val name: String
)