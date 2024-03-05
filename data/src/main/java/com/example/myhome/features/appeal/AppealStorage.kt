package com.example.myhome.features.appeal

import com.example.myhome.features.event.EventApiService
import com.example.myhome.features.event.dto.EventListRequest
import com.example.myhome.features.event.models.EventType

class AppealStorage(
    private val appealApiService: AppealApiService,
    private val eventApiService: EventApiService
) {
    suspend fun addAppeal(appeal: AppealAddRequest): Boolean {
        val result = appealApiService.addAppeal(appeal)
        if (result != null) {
            return true
        }
        return false
    }

    suspend fun listAppeal(): List<AppealAddResponse> {
        val events = eventApiService.listEvent(
            EventListRequest(
            userId = 1,
            events = listOf(EventType.Appeal)
            )
        )
        return events.appeals.appeals
    }
}