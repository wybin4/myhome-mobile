package com.example.myhome.event.storages

import com.example.myhome.event.dto.EventGetDto

interface EventStorage {
    suspend fun listEvent(): EventGetDto
}