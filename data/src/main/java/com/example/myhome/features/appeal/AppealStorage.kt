package com.example.myhome.features.appeal

class AppealStorage(
    private val appealApiService: AppealApiService
) {
    suspend fun addAppeal(appeal: AppealAddRequest): Boolean {
        val result = appealApiService.addAppeal(appeal)
        if (result != null) {
            return true
        }
        return false
    }

}