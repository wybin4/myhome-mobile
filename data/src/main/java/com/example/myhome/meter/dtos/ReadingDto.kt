package com.example.myhome.meter.dtos

abstract class ReadingDto(
    open val id: Int?,
    open val meterId: Int,
    open val reading: Double,
    open val readAt: String
)

data class ReadingAddDto(
    override val id: Int?,
    override val meterId: Int,
    override val reading: Double,
    override val readAt: String
) : ReadingDto(
    id = id,
    meterId = meterId,
    reading = reading,
    readAt = readAt
)

data class ReadingGetDto(
    val id: Int,
    val reading: Double,
    val consumption: Double,
    val readAt: String
)

data class ReadingListDto(
    val meterId: Int
)