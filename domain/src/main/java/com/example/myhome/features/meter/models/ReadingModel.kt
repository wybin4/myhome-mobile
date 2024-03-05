package com.example.myhome.features.meter.models

import java.util.Date

data class ReadingAddModel(
    val meterId: Int,
    val reading: Double,
    val readAt: Date
)

data class ReadingListItemModel(
    val id: Int,
    val reading: Double,
    val consumption: Double,
    val readAt: Date
)