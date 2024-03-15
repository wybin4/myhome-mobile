package com.example.myhome.presentation.features.meter.converters

import com.example.myhome.features.meter.dtos.ReadingAddRequest
import java.util.Date

interface ReadingRemoteConverter {
    fun addToRemote(meterId: Int, reading: Double): ReadingAddRequest {
        return ReadingAddRequest(
            meterId = meterId,
            reading = reading,
            readAt = Date().toString()
        )
    }

}