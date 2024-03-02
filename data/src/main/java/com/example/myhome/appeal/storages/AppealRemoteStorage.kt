package com.example.myhome.appeal.storages


import com.example.myhome.appeal.dtos.AppealAddDto
import com.example.myhome.appeal.dtos.AppealGetDto
import com.example.myhome.appeal.services.AppealApiService
import com.example.myhome.event.dto.EventListDto
import com.example.myhome.event.models.EventType
import com.example.myhome.event.services.EventApiService

class AppealRemoteStorage(
    private val appealApiService: AppealApiService,
    private val eventApiService: EventApiService
): AppealStorage {
    override suspend fun addAppeal(appeal: AppealAddDto): Boolean {
        val result = appealApiService.addAppeal(appeal)
        if (result != null) {
            return true
        }
        return false
    }

    override suspend fun listAppeal(): List<AppealGetDto> {
        val events = eventApiService.listEvents(EventListDto(
            userId = 1,
            events = listOf(EventType.Appeal)
        ))
        return events.appeals.appeals
    }
}