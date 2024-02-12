package com.example.myhome.meter.models

import com.example.myhome.common.models.Adaptive
import com.example.myhome.common.models.DateConverter
import java.util.Date

abstract class ReadingModel(
    open val id: Int?,
    open val meterId: Int,
    open val reading: Float,
    open val readAt: Date
)

data class ReadingAddModel(
    override val id: Int?,
    override val meterId: Int,
    override val reading: Float,
    override val readAt: Date
) : ReadingModel(
    id = id,
    meterId = meterId,
    reading = reading,
    readAt = readAt
)

data class ReadingGetModel(
    val id: Int,
    val reading: Float,
    val consumption: Float,
    val readAt: Date
)