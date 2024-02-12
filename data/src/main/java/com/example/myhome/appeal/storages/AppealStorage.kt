package com.example.myhome.appeal.storages

import com.example.myhome.appeal.dtos.AppealAddDto

interface AppealStorage {
    suspend fun addAppeal(appeal: AppealAddDto)
}