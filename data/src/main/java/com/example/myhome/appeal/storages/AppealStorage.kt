package com.example.myhome.appeal.storages

import com.example.myhome.appeal.dtos.AppealAddDto
import com.example.myhome.appeal.dtos.AppealGetDto

interface AppealStorage {
    suspend fun addAppeal(appeal: AppealAddDto): Boolean
    suspend fun listAppeal(): List<AppealGetDto>
}