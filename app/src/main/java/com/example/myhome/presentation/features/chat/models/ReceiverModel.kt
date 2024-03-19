package com.example.myhome.presentation.features.chat.models

import com.example.myhome.models.UserRole
import com.example.myhome.presentation.models.Adaptive
import com.example.myhome.presentation.utils.pickers.CharPicker

data class ReceiverUiModel(
    override val id: Int,
    val role: UserRole,
    val name: String
) : Adaptive, CharPicker {
    fun formatName(): String {
        return processString(name)
    }

    fun formatUserRole(): String {
        return when(role) {
            UserRole.Owner -> "Собственник"
            UserRole.ManagementCompany -> "Управляющая компания"
            else -> "Товарищ"
        }
    }
}