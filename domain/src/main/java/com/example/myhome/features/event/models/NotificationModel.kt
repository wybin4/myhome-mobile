package com.example.myhome.features.event.models

import java.util.Date

data class HouseNotificationListItemModel (
    val id: Int,
    val title: String,
    val type: HouseNotificationType,
    val createdAt: Date,
    val text: String,
    val managementCompanyName: String
)