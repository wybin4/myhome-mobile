package com.example.myhome.event.models

import java.util.Date

data class HouseNotificationGetModel (
    val id: Int,
    val title: String,
    val type: HouseNotificationType,
    val createdAt: Date,
    val text: String,
    val managementCompanyName: String
)