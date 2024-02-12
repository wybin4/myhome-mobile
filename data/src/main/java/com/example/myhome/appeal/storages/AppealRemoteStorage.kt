package com.example.myhome.appeal.storages

import com.example.myhome.appeal.dtos.AppealAddDto
import com.example.myhome.appeal.services.AppealApiService

class AppealRemoteStorage(private val appealApiService: AppealApiService): AppealStorage {
    override suspend fun addAppeal(appeal: AppealAddDto) {
        appealApiService.addAppeal(appeal)
    }
}