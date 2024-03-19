package com.example.myhome.presentation.features.chat.converters

import com.example.myhome.features.chat.ChatAddRequest
import com.example.myhome.features.chat.ChatAddRequestItem
import com.example.myhome.presentation.features.chat.models.ReceiverUiModel

interface ChatRemoteConverter {
    fun addToRemote(receiver: ReceiverUiModel): ChatAddRequest {
        return ChatAddRequest(
            users = listOf(
                ChatAddRequestItem(
                    userId = receiver.id, userRole = receiver.role
                )
            )
        )
    }
}