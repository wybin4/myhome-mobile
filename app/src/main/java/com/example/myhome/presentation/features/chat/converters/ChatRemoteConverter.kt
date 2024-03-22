package com.example.myhome.presentation.features.chat.converters

import com.example.myhome.features.chat.dtos.ChatAddRequest
import com.example.myhome.features.chat.dtos.ChatAddRequestItem
import com.example.myhome.features.chat.dtos.MessageAddRequest
import com.example.myhome.features.chat.dtos.MessageReadRequest
import com.example.myhome.presentation.features.chat.models.ReceiverUiModel

interface ChatRemoteConverter {
    fun chatAddToRemote(receiver: ReceiverUiModel): ChatAddRequest {
        return ChatAddRequest(
            users = listOf(
                ChatAddRequestItem(
                    userId = receiver.id, userRole = receiver.role
                )
            )
        )
    }

    fun messageAddToRemote(chatId: Int, text: String, createdAt: Long): MessageAddRequest {
        return MessageAddRequest(
            chatId = chatId,
            text = text,
            senderId = 1,
            createdAt = createdAt
        )
    }

    fun messageReadToRemote(chatId: Int, messageId: Int): MessageReadRequest {
        return MessageReadRequest(
            chatId = chatId,
            messageId = messageId,
            senderId = 1
        )
    }
}