package com.example.myhome.presentation.features.chat.converters

import com.example.myhome.features.chat.ChatListItemResponse
import com.example.myhome.presentation.features.chat.models.ChatAddToGetParcelableModel

interface ChatParcelableConverter {
    fun chatAddToGetParcel(chat: ChatListItemResponse): ChatAddToGetParcelableModel {
        return ChatAddToGetParcelableModel(
            id = chat.id,
            receiverName = chat.receiverName,
            createdAt = chat.formatCreatedAt()
        )
    }
}