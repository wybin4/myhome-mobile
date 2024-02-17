package com.example.myhome.meter.models

import java.util.Date

data class ReadingAddModel(
    val meterId: Int,
    val reading: Double,
    val readAt: Date
)

data class ReadingGetModel(
    val id: Int,
    val reading: Double,
    val consumption: Double,
    val readAt: Date
)